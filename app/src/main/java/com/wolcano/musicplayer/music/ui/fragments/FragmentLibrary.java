package com.wolcano.musicplayer.music.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kabouzeid.appthemehelper.ThemeStore;
import com.kabouzeid.appthemehelper.common.ATHToolbarActivity;
import com.kabouzeid.appthemehelper.util.TintHelper;
import com.kabouzeid.appthemehelper.util.ToolbarContentTintHelper;
import com.mopub.mobileads.MoPubView;
import com.wolcano.musicplayer.music.R;
import com.wolcano.musicplayer.music.mvp.DisposableManager;
import com.wolcano.musicplayer.music.utils.Perms;
import com.wolcano.musicplayer.music.widgets.StatusBarView;
import com.wolcano.musicplayer.music.ui.adapter.LibraryFragmentPagerAdapter;
import com.wolcano.musicplayer.music.ui.fragments.innerfragment.FragmentAlbums;
import com.wolcano.musicplayer.music.ui.fragments.innerfragment.FragmentArtists;
import com.wolcano.musicplayer.music.ui.fragments.innerfragment.FragmentGenres;
import com.wolcano.musicplayer.music.ui.fragments.innerfragment.FragmentSongs;
import com.wolcano.musicplayer.music.utils.Utils;

public class FragmentLibrary extends BaseFragment {

    StatusBarView statusBarView;
    Toolbar toolbar;
    private boolean isHidden = false;
    private MoPubView moPubView;
    public ViewPager viewPager;
    private Handler handlerInit;
    private Runnable runnableInit;
    private Context context;
    AppBarLayout appBarLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mylibrary, container, false);
        setHasOptionsMenu(true);
        context = getContext();
        appBarLayout = view.findViewById(R.id.appbar);
        if (Utils.getIsMopubInitDone(context.getApplicationContext())) {
            moPubView = (MoPubView) view.findViewById(R.id.adview);
            moPubView.setAdUnitId("f3b55e3961424c73bbf04175a179fe6b"); // Enter your Ad Unit ID from www.mopub.com
            moPubView.loadAd();

        } else {
            handlerInit = new Handler();
            runnableInit = new Runnable() {
                @Override
                public void run() {
                    if (Utils.getIsMopubInitDone(context.getApplicationContext())) {
                        moPubView = (MoPubView) view.findViewById(R.id.adview);
                        moPubView.setAdUnitId("f3b55e3961424c73bbf04175a179fe6b"); // Enter your Ad Unit ID from www.mopub.com
                        moPubView.loadAd();
                    } else {
                        handlerInit.postDelayed(this::run, 500);
                    }


                }
            };
            handlerInit.postDelayed(runnableInit, 500);
        }


        toolbar = view.findViewById(R.id.toolbar);
        statusBarView = view.findViewById(R.id.statusBarCustom);
        int color = Utils.getPrimaryColor(getContext());
        setStatusbarColorAuto(statusBarView, color);

        if (Build.VERSION.SDK_INT < 21 && view.findViewById(R.id.statusBarCustom) != null) {
            view.findViewById(R.id.statusBarCustom).setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= 19) {
                int statusBarHeight = Utils.getStatHeight(getContext());
               // FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
                //toolbar.setPadding(0, statusBarHeight, 0, 0);

              //  toolbar.setLayoutParams(layoutParams);
                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                params.setMargins(0, statusBarHeight, 0, 0);

                params.setScrollFlags(0);
                toolbar.setLayoutParams(params);

            }
        }

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(color);
        if (Utils.isColorLight(color)) {
            toolbar.setTitleTextColor(Color.BLACK);
        } else {
            toolbar.setTitleTextColor(Color.WHITE);
        }


        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.library);
        if (toolbar.getNavigationIcon() != null) {
            toolbar.setNavigationIcon(TintHelper.createTintedDrawable(toolbar.getNavigationIcon(), ToolbarContentTintHelper.toolbarContentColor(getContext(), color)));
        }
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        LibraryFragmentPagerAdapter adapter = new LibraryFragmentPagerAdapter(getContext(), getChildFragmentManager());
        viewPager.setAdapter(adapter);
        //   viewPager.setOffscreenPageLimit(4);
        viewPager.setSaveFromParentEnabled(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    Fragment fragment = (Fragment) adapter.instantiateItem(viewPager, 0);

                    if (fragment != null && fragment instanceof FragmentSongs) {
                        if (isHidden)
                            ((FragmentSongs) fragment).handleOptionsMenu();


                    } else if (fragment != null && fragment instanceof FragmentArtists) {
                        if (isHidden)
                            ((FragmentArtists) fragment).handleOptionsMenu();

                    } else if (fragment != null && fragment instanceof FragmentAlbums) {
                        if (isHidden)
                            ((FragmentAlbums) fragment).handleOptionsMenu();

                    } else if (fragment != null && fragment instanceof FragmentGenres) {
                        if (isHidden)
                            ((FragmentGenres) fragment).handleOptionsMenu();

                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.removeAllViews();
        //  checkPerm(adapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(color);
        int normalColor = ToolbarContentTintHelper.toolbarSubtitleColor(getActivity(), color);
        int selectedColor = ToolbarContentTintHelper.toolbarTitleColor(getActivity(), color);
        tabLayout.setTabTextColors(normalColor, selectedColor);
        tabLayout.setSelectedTabIndicatorColor(ThemeStore.accentColor(getActivity()));


        return view;
    }

    public void addOnAppBarOffsetChangedListener(AppBarLayout.OnOffsetChangedListener onOffsetChangedListener) {
        appBarLayout.addOnOffsetChangedListener(onOffsetChangedListener);
    }

    public void removeOnAppBarOffsetChangedListener(AppBarLayout.OnOffsetChangedListener onOffsetChangedListener) {
        appBarLayout.removeOnOffsetChangedListener(onOffsetChangedListener);
    }

    public int getTotalAppBarScrollingRange() {
        return appBarLayout.getTotalScrollRange();
    }

    private void checkPerm(LibraryFragmentPagerAdapter adapter) {
        Perms.with(this)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .result(new Perms.PermInterface() {
                    @Override
                    public void onPermGranted() {
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onPermUnapproved() {
                        adapter.notifyDataSetChanged();
                    }
                })
                .reqPerm();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (moPubView != null) {
            moPubView.destroy();
        }
        viewPager.removeAllViews();
        viewPager.destroyDrawingCache();
        viewPager = null;
        statusBarView = null;
        toolbar = null;
        if (handlerInit != null && runnableInit != null) {
            handlerInit.removeCallbacks(runnableInit);
        }
        DisposableManager.dispose();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            isHidden = true;
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        ToolbarContentTintHelper.handleOnCreateOptionsMenu(getActivity(), toolbar, menu, ATHToolbarActivity.getToolbarBackgroundColor(toolbar));

    }

}