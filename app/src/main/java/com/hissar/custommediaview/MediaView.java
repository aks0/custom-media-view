package com.hissar.custommediaview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import junit.framework.Assert;

/**
 * Custom view to render a media attachment along with texts and a custom icon.
 */
public class MediaView extends ViewGroup {

    private static final int CHILD_COUNT = 4;

    public MediaView(Context context) {
        this(context, null);
    }

    public MediaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Assert.assertEquals(CHILD_COUNT, getChildCount());

        // Get the available size for the largest child.
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int childRight = getMeasuredWidth() - getPaddingRight();
        int childBottom = getMeasuredHeight() - getPaddingBottom();

        int maxChildWidth = childRight - childLeft;
        int maxChildHeight = childBottom - childTop;

        // Measure the top media attachment view
        View mediaAttachmentView = getChildAt(0);
        measureAndLayoutMediaAttachmentView(mediaAttachmentView, maxChildWidth, maxChildHeight);

        // Measure the two text views describing the image
        View titleTextView = getChildAt(1);
        View descriptionTextView = getChildAt(2);
        measureAndLayoutTextViews(
            titleTextView,
            descriptionTextView,
            mediaAttachmentView,
            maxChildWidth,
            maxChildHeight);

        // Measure the image icon
        View imageIcon = getChildAt(3);
        measureAndLayoutImageIcon(
            imageIcon,
            mediaAttachmentView,
            titleTextView,
            descriptionTextView,
            maxChildWidth,
            maxChildHeight);
    }

    private void measureAndLayoutMediaAttachmentView(
        View mediaAttachmentView,
        int maxChildWidth,
        int maxChildHeight) {

        mediaAttachmentView.measure(
            MeasureSpec.makeMeasureSpec(maxChildWidth, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec((int) (0.9 * maxChildHeight), MeasureSpec.AT_MOST));

        mediaAttachmentView.layout(
            getPaddingLeft(),
            getPaddingTop(),
            getPaddingLeft() + mediaAttachmentView.getMeasuredWidth(),
            getPaddingTop() + mediaAttachmentView.getMeasuredHeight());
    }

    private void measureAndLayoutTextViews(
        View titleTextView,
        View descriptionTextView,
        View mediaAttachmentView,
        int maxChildWidth,
        int maxChildHeight) {

        titleTextView.measure(
            MeasureSpec.makeMeasureSpec(maxChildWidth, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(maxChildHeight, MeasureSpec.AT_MOST));
        titleTextView.layout(
            getPaddingLeft(),
            getPaddingTop() + mediaAttachmentView.getMeasuredHeight(),
            getPaddingLeft() + titleTextView.getMeasuredWidth(),
            getPaddingTop() +
                mediaAttachmentView.getMeasuredHeight() + titleTextView.getMeasuredHeight());

        descriptionTextView.measure(
            MeasureSpec.makeMeasureSpec(maxChildWidth, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(maxChildHeight, MeasureSpec.AT_MOST));

        descriptionTextView.layout(
            getPaddingLeft(),
            getPaddingTop() + mediaAttachmentView.getMeasuredHeight()
                + titleTextView.getMeasuredHeight(),
            getPaddingLeft() + descriptionTextView.getMeasuredWidth(),
            getPaddingTop() +
                mediaAttachmentView.getMeasuredHeight() +
                titleTextView.getMeasuredHeight() + descriptionTextView.getMeasuredHeight());
    }

    private void measureAndLayoutImageIcon(
        View imageIcon,
        View mediaAttachmentView,
        View titleTextView,
        View descriptionTextView,
        int maxChildWidth,
        int maxChildHeight) {

        imageIcon.measure(
            MeasureSpec.makeMeasureSpec(maxChildWidth, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(maxChildHeight, MeasureSpec.AT_MOST));

        int imageIconTop =
            getPaddingTop() + mediaAttachmentView.getMeasuredHeight() +
                (titleTextView.getMeasuredHeight() + descriptionTextView.getMeasuredHeight()
                    - imageIcon.getMeasuredHeight()) / 2;
        imageIcon.layout(
            getPaddingLeft() + mediaAttachmentView.getMeasuredWidth()
                - imageIcon.getMeasuredWidth(),
            imageIconTop,
            getPaddingLeft() + mediaAttachmentView.getMeasuredWidth(),
            imageIconTop + imageIcon.getMeasuredHeight());
    }
}
