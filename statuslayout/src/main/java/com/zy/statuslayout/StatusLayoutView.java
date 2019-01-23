package com.zy.statuslayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 作者：赵岩 on 2019/1/23 14:41
 */
public class StatusLayoutView extends FrameLayout {

    private static final LayoutParams DEFAULT_LAYOUT_PARAMS =
            new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    /**
     * 布局的资源ID
     */
    private int contentLayoutResId;
    private int errorLayoutResId;
    private int emptyLayoutResId;
    private int networkLayoutResId;
    private int loadingLayoutResId;

    /**
     * inflater后的View
     */
    private View mContentLayout;
    private View mErrorLayout;
    private View mEmptyLayout;
    private View mNetWorkLayout;
    private View mLoadingLayout;

    private LayoutInflater mInflater;
    private ViewStatus mViewStatus;
    private static final int NULL_RESOURCE_ID = -1;

    public StatusLayoutView(@NonNull Context context) {
        this(context, null);
    }

    public StatusLayoutView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayoutView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StatusLayoutView, defStyleAttr, 0);
        contentLayoutResId = ta.getResourceId(R.styleable.StatusLayoutView_layout_content, R.layout.layout_content);
        errorLayoutResId = ta.getResourceId(R.styleable.StatusLayoutView_layout_error, R.layout.layout_error);
        emptyLayoutResId = ta.getResourceId(R.styleable.StatusLayoutView_layout_empty, R.layout.layout_empty);
        networkLayoutResId = ta.getResourceId(R.styleable.StatusLayoutView_layout_network, R.layout.layout_network);
        loadingLayoutResId = ta.getResourceId(R.styleable.StatusLayoutView_layout_loading, R.layout.layout_loading);
        ta.recycle();
        mInflater = LayoutInflater.from(getContext());
        initContent();
    }

    private void initContent() {
        mContentLayout = mInflater.inflate(contentLayoutResId, null);
        addView(mContentLayout, 0, DEFAULT_LAYOUT_PARAMS);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showContent();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clear(mEmptyLayout, mLoadingLayout, mErrorLayout, mNetWorkLayout);
        mInflater = null;
    }

    /**
     * 展示内容
     */
    public void showContent() {
        showView(mContentLayout, contentLayoutResId, ViewStatus.VIEW_STATUS_CONTENT);
    }

    /**
     * error
     */
    public void showError() {
        showView(mErrorLayout, errorLayoutResId, ViewStatus.VIEW_STATUS_ERROR);
    }

    /**
     * Empty
     */
    public void showEmpty() {
        showView(mEmptyLayout, emptyLayoutResId, ViewStatus.VIEW_STATUS_EMPTY);
    }

    /**
     * loading
     */
    public void showLoading() {
        showView(mLoadingLayout, loadingLayoutResId, ViewStatus.VIEW_STATUS_LOADING);
    }

    /**
     * network
     */
    public void showNetwork() {
        showView(mNetWorkLayout, networkLayoutResId, ViewStatus.VIEW_STATUS_NETWORK);
    }

    private void showView(View view, int viewResId, ViewStatus status) {
        mViewStatus = status;
        if (null == view && viewResId != NULL_RESOURCE_ID) {
            view = mInflater.inflate(viewResId, null);
            addView(view, 0, DEFAULT_LAYOUT_PARAMS);
        }
        showViewById(view.getId());
        if (status == ViewStatus.VIEW_STATUS_NETWORK) {
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (layoutClick != null) layoutClick.onOpenRequestListener();
                }
            });
        } else if (status == ViewStatus.VIEW_STATUS_ERROR) {
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (layoutClick != null) layoutClick.onTryRequestListener();
                }
            });
        }

    }

    /**
     * 出入的view id，展示 其他隐藏
     *
     * @param viewId
     */
    private void showViewById(int viewId) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(view.getId() == viewId ? View.VISIBLE : View.GONE);
            //if (view.getId() != viewId) removeView(view);  性能存在问题
        }
    }

    private void clear(View... views) {
        for (View view : views) {
            if (null != view) {
                removeView(view);
            }
        }
    }

    public ViewStatus getViewStatus() {
        return mViewStatus;
    }

    enum ViewStatus {
        VIEW_STATUS_LOADING,
        VIEW_STATUS_EMPTY,
        VIEW_STATUS_ERROR,
        VIEW_STATUS_CONTENT,
        VIEW_STATUS_NETWORK,
    }

    private LayoutClick layoutClick;

    public void setLayoutClick(LayoutClick layoutClick) {
        this.layoutClick = layoutClick;
    }

    public interface LayoutClick {
        void onTryRequestListener();

        void onOpenRequestListener();
    }

    public View getContentLayout() {
        return mContentLayout;
    }

    public View getErrorLayout() {
        return mErrorLayout;
    }

    public View getEmptyLayout() {
        return mEmptyLayout;
    }

    public View getNetWorkLayout() {
        return mNetWorkLayout;
    }

    public View getLoadingLayout() {
        return mLoadingLayout;
    }
}
