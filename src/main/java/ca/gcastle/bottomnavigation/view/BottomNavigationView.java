package ca.gcastle.bottomnavigation.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import ca.gcastle.bottomnavigation.R;

/**
 * Created by graeme.castle on 12/04/2016.
 */
public class BottomNavigationView extends FrameLayout {

    private GestureDetector mDetector;
    private Paint           mRadiusPaint;

    // Customisable Values
    private boolean growTabs;
    private int tabGrowthModifier;
    private boolean showReveal;

    private int unselectedChildSize;

    private boolean currentlyAnimating = false;
    private float radiusAnimationValue = 0;

    // Currently selected and next to animate indeces
    private int currentlySelectedChild = 0;
    private int itemToOpenAfterThisAnimation = -1;

    // Ripple centers (both current and next)
    private int cx;
    private int cy;

    private int cxAfterThisAnimation = 0;
    private int cyAfterThisAnimation = 0;

    private ArrayList<Integer> childWidths = new ArrayList<>();

    private static final int INITIALLY_SELECTED_CHILD = 0;
    private static final int EXPAND_TIME = 200;

    public BottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDetector = new GestureDetector(context, listener);
        mRadiusPaint = new Paint();

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.bottomNav);

        growTabs   = a.getBoolean(R.styleable.bottomNav_navGrowTabs, true);
        showReveal = a.getBoolean(R.styleable.bottomNav_navShowCircleReveal, true);
        if(growTabs) {
            tabGrowthModifier = (int) a.getDimension(R.styleable.bottomNav_navGrowthModifier, (int) BottomNavigationUtils.getPixelsFromDP(getContext(), 64));
        } else {
            tabGrowthModifier = 0;
        }

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for(int i  = 0; i < getChildCount(); i++) {
            if(!(getChildAt(i) instanceof BottomNavigationTabView)) {
                throw new ClassCastException(getClass().getSimpleName() + " requires only " + BottomNavigationTabView.class.getSimpleName());
            }
            if(i == INITIALLY_SELECTED_CHILD) {
                ((BottomNavigationTabView)getChildAt(i)).setSelected();
            }
        }

        if(getChildCount() < 2) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " requires at least two children");
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        int height = b - t;
        setChildSizes(changed, width);
        int left = 0;
        for(int i = 0; i < getChildCount(); i++) {
            int childWidth = childWidths.get(i);
            getChildAt(i).layout(left, 0, left + childWidth, height);
            left += childWidth;
        }
    }

    private void setChildSizes(boolean changed, int w) {
        if(changed) {
            childWidths.clear();
            unselectedChildSize = (w - tabGrowthModifier) / getChildCount();
            //Log.e(BottomNavigationView.class.getSimpleName(), "Screen Width: " + w + ", Child Size: " + unselectedChildSize + ", ExpandedModifier: " + tabGrowthModifier);
            for(int i = 0; i < getChildCount(); i++) {
                if(i == INITIALLY_SELECTED_CHILD) {
                    childWidths.add(unselectedChildSize + tabGrowthModifier);
                } else {
                    childWidths.add(unselectedChildSize);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return true;
    }

    GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int widthPerChild = getWidth() / getChildCount();
            int childClicked = (int) (e.getX() / widthPerChild);

            if(currentlyAnimating) {
                itemToOpenAfterThisAnimation = childClicked;
                cxAfterThisAnimation = (int) e.getX();
                cyAfterThisAnimation = (int) e.getY();
            } else {
                if(childClicked != currentlySelectedChild) {
                    cx = (int) e.getX();
                    cy = (int) e.getY();
                    animateChildToSelected(childClicked);
                }
            }
            return true;
        }
    };

    private void animateChildToSelected(final int child) {
        currentlyAnimating = true;
        mRadiusPaint.setColor(((BottomNavigationTabView)getChildAt(child)).getColor());

        ValueAnimator expandAnimator = null;

        if(growTabs) {
            expandAnimator = ValueAnimator.ofInt(0, tabGrowthModifier);
            expandAnimator.addUpdateListener(new ViewWidthAnimator(child, currentlySelectedChild));
        }

        Animator maximiseAnimators = ((BottomNavigationTabView) getChildAt(child)).getAnimatorSet(true);
        Animator minimiseAnimators = ((BottomNavigationTabView) getChildAt(currentlySelectedChild)).getAnimatorSet(false);

        ValueAnimator radiusAnimator = null;
        if(showReveal) {
            radiusAnimator = ValueAnimator.ofFloat(0, getWidth());
            radiusAnimator.addUpdateListener(new RadiusAnimationListener());
        } else {
            setBackgroundColor(((BottomNavigationTabView)getChildAt(child)).getColor());
        }

        List<Animator> animations = new ArrayList<>();

        if(expandAnimator != null) animations.add(expandAnimator);
        if(maximiseAnimators != null) animations.add(maximiseAnimators);
        if(minimiseAnimators != null) animations.add(minimiseAnimators);
        if(radiusAnimator != null) animations.add(radiusAnimator);

        if(animations.size() > 0) {
            AnimatorSet set = new AnimatorSet();
            set.addListener(new EndAnimationListener());
            set.setDuration(EXPAND_TIME);
            set.playTogether(animations);
            set.start();
        }

        currentlySelectedChild = child;
    }

    private class RadiusAnimationListener implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            radiusAnimationValue = (float) animation.getAnimatedValue();
        }
    }

    private class EndAnimationListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationEnd(Animator animation) {
            radiusAnimationValue = 0;
            setBackgroundColor(mRadiusPaint.getColor());
            currentlyAnimating = false;
            if(itemToOpenAfterThisAnimation != -1) {
                cx = cxAfterThisAnimation;
                cy = cyAfterThisAnimation;

                animateChildToSelected(itemToOpenAfterThisAnimation);
                itemToOpenAfterThisAnimation = -1;
                cxAfterThisAnimation = 0;
                cyAfterThisAnimation = 0;
            }
        }

        @Override public void onAnimationStart(Animator animation) {}
        @Override public void onAnimationCancel(Animator animation) {}
        @Override public void onAnimationRepeat(Animator animation) {}
    }

    private class ViewWidthAnimator implements ValueAnimator.AnimatorUpdateListener {
        int childIndexToExpand;
        int childIndexToMinimise;
        public ViewWidthAnimator(int childIndexToExpand, int childIndexToMinimise) {
            this.childIndexToExpand = childIndexToExpand;
            this.childIndexToMinimise = childIndexToMinimise;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            childWidths.set(childIndexToExpand, unselectedChildSize + (Integer) animation.getAnimatedValue());
            childWidths.set(childIndexToMinimise, unselectedChildSize + (tabGrowthModifier - (Integer) animation.getAnimatedValue()));
            requestLayout();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(currentlyAnimating) {
            canvas.drawCircle(cx, cy, radiusAnimationValue, mRadiusPaint);
        }
    }
}
