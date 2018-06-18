package com.example.android.bogisbakingappv2;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by Bogi on 2018. 05. 07..
 */

@SuppressWarnings("deprecation")
public class FragmentStep extends Fragment implements ExoPlayer.EventListener {

    public static TextView lepes;
    public static String videoString;

    public void setVideoString(String videoString) {
        FragmentStep.videoString = videoString;
    }

    String TAG = "FragmentStep";
    DataStep step;
    LinearLayout lay;
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    CardView descriptionCard;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    ImageView andr;
    BottomNavigationView mNavi;
    public static int tempSelection;
    public static int telSel;
    String video;
    int orientation;

    public long poz;
    private final String POZ_KEY = "poz";

    public FragmentStep() {
    }

    public void videoLoad() {
        //Log.e(TAG, "1 videoLoad " + poz);
        if (step != null) {
            if (!ActivityMain.tabletSize) {
                video = step.getVideoUrl();
            } else {
                video = videoString;
            }
        } else {
            video = "";
        }


        orientation = getResources().getConfiguration().orientation;

        if (video != null && !video.isEmpty()) {
            // Init and show video view
            setViewVisibility(exoPlayerView, true);
            setViewVisibility(andr, false);
            initializeMediaSession();
            initializePlayer(Uri.parse(video));

            if (orientation == Configuration.ORIENTATION_LANDSCAPE && !ActivityMain.tabletSize) {
                expandVideoView(exoPlayerView);
                setViewVisibility(descriptionCard, false);
                setViewVisibility(mNavi, false);
                hideSystemUI();
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) exoPlayerView.getLayoutParams();
                expandVideoView(exoPlayerView);
                exoPlayerView.setLayoutParams(params);

            } else {

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) exoPlayerView.getLayoutParams();
                exoPlayerView.setLayoutParams(params);


            }
        } else {
            // Hide video view
            setViewVisibility(exoPlayerView, false);
            setViewVisibility(andr, true);
        }

    }

    //**********BOTTOMNAVIGATION*************
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {


                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {

                        case R.id.navigation_left:
                            poz = 0;
                            BottomNavigationViewHelper.disableShiftMode(mNavi);
                            if (tempSelection > 0 || telSel > 0) {
                                if (!ActivityMain.tabletSize) {
                                    telSel = telSel - 1;
                                    step = FragmentDetail.lepesek.get(telSel);
                                    lepes.setText(FragmentDetail.lepesek.get(telSel).getDescription());
                                    releasePlayer();
                                    videoLoad();
                                } else {
                                    tempSelection = tempSelection - 1;
                                    lepes.setText(FragmentDetail.lepesek.get(tempSelection).getDescription());
                                    video = FragmentDetail.lepesek.get(tempSelection).getVideoUrl();

                                    if (video != null && !video.equals("")) {
                                        setViewVisibility(exoPlayerView, true);
                                        setViewVisibility(andr, false);
                                        releasePlayer();
                                        initializePlayer(Uri.parse(video));
                                        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) exoPlayerView.getLayoutParams();
                                        exoPlayerView.setLayoutParams(params);
                                    } else {
                                        setViewVisibility(exoPlayerView, false);
                                        setViewVisibility(andr, true);
                                    }
                                }
                            }
                            return true;
                        case R.id.navigation_right:
                            poz = 0;

                            BottomNavigationViewHelper.disableShiftMode(mNavi);
                            if (tempSelection < FragmentDetail.lepesek.size() - 1 && telSel < FragmentDetail.lepesek.size()) {
                                if (!ActivityMain.tabletSize) {
                                    telSel = telSel + 1;
                                    step = FragmentDetail.lepesek.get(telSel);
                                    lepes.setText(step.getDescription());
                                    releasePlayer();
                                    videoLoad();
                                } else {
                                    tempSelection = tempSelection + 1;

                                    lepes.setText(FragmentDetail.lepesek.get(tempSelection).getDescription());
                                    video = FragmentDetail.lepesek.get(tempSelection).getVideoUrl();

                                    if (video != null && !video.equals("")) {
                                        setViewVisibility(exoPlayerView, true);
                                        setViewVisibility(andr, false);
                                        releasePlayer();
                                        initializePlayer(Uri.parse(video));
                                        ConstraintLayout.LayoutParams params =
                                                (ConstraintLayout.LayoutParams) exoPlayerView.getLayoutParams();
                                        exoPlayerView.setLayoutParams(params);

                                    } else {
                                        setViewVisibility(exoPlayerView, false);
                                        setViewVisibility(andr, true);
                                    }
                                }
                            }
                            return true;
                    }
                    return true;
                }
            };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        if (exoPlayer != null) {
            //Log.e(TAG, "2 onSaveInstanceState " + poz);
            outState.putLong(POZ_KEY, poz);
        } else {
            outState.putLong(POZ_KEY, poz);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Log.e(TAG, "3 onCreateView eleje " + poz);
        View view = inflater.inflate(R.layout.fragment_single_step, container, false);
        lepes = view.findViewById(R.id.recipe_step);
        lay = view.findViewById(R.id.reszletes_lepes_layout);
        mNavi = view.findViewById(R.id.navigation);

        try {
            if (ActivityMain.tabletSize) {
                step = FragmentDetail.lepesek.get(tempSelection);
            } else {
                step = FragmentDetail.lepesek.get(telSel);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        exoPlayerView = view.findViewById(R.id.recipe_step_video);
        descriptionCard = view.findViewById(R.id.recipe_step_desc_card);
        mNavi.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        andr = view.findViewById(R.id.android_eats_apple_image);

        BottomNavigationViewHelper.disableShiftMode(mNavi);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            poz = savedInstanceState.getLong(POZ_KEY);
            Log.e(TAG, "poz: " + poz);
            Log.e("tabSize?", "" + ActivityMain.tabletSize);
            if (!ActivityMain.tabletSize) {
                lepes.setText(step.getDescription());
                videoLoad();
            }

        }
        try {
            if (ActivityMain.tabletSize) {
                lepes.setText(step.getDescription());

            } else {
                lepes.setText(step.getDescription());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //Log.e(TAG, "4 onCreateView vége " + poz);
        return view;
    }//onCreate vége

    private void setViewVisibility(View view, boolean show) {
        //Log.e(TAG, "5 setViewVisibility " + poz);
        if (show) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void initializePlayer(Uri mediaUri) {
        //Log.e(TAG, "6 initializePlayer(Uri) " + poz);

        if (exoPlayer == null) {
            exoPlayerView.requestFocus();
            TrackSelector trackSelector = new DefaultTrackSelector();
            try {
                exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity().getApplicationContext(), trackSelector);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), "StepVideo");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.seekTo(poz);
            exoPlayer.setPlayWhenReady(true);
        } else {
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Log.e(TAG, "7 onConfigurationChanged" + poz);
        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            expandVideoView(exoPlayerView);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) exoPlayerView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = 600;
            exoPlayerView.setLayoutParams(params);
        }
    }

    // FROM EXOPLAYER CODELABS
    private void initializeMediaSession() {
        //Log.e(TAG, "8 initializeMediaSession " + poz);
        mediaSession = new MediaSessionCompat(getContext(), "SingleStepPage");

        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());

        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                exoPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                exoPlayer.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                exoPlayer.seekTo(0);
            }
        });
        mediaSession.setActive(true);
    }

    private void expandVideoView(SimpleExoPlayerView exoPlayer) {
        Log.e(TAG, " 9 " + poz);
        exoPlayer.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        exoPlayer.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    //  https://developer.android.com/training/system-ui/immersive.html
    private void hideSystemUI() {

        try {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
            );
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        //Log.e(TAG, "onPlayerSatateChanged 11 " + poz);
        if (exoPlayer != null) {
            if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
                stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, exoPlayer.getCurrentPosition(), 1f);
            } else if ((playbackState == ExoPlayer.STATE_READY)) {
                stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, exoPlayer.getCurrentPosition(), 1f);
            }
        }
        mediaSession.setPlaybackState(stateBuilder.build());
        poz = exoPlayer.getCurrentPosition();
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity(int reason) {
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }

    @Override
    public void onSeekProcessed() {
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.e(TAG, "onResume 12 " + poz);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializeMediaSession();
        //Log.e(TAG, "13 onStart" + poz);
        if (step != null) {
            if (!ActivityMain.tabletSize) {
                video = step.getVideoUrl();
            } else {
                video = videoString;
            }
        } else {
            video = "";
        }

        orientation = getResources().getConfiguration().orientation;

        if (video != null && !video.isEmpty()) {
            // Init and show video view
            setViewVisibility(exoPlayerView, true);
            setViewVisibility(andr, false);
            initializeMediaSession();
            initializePlayer(Uri.parse(video));

            if (orientation == Configuration.ORIENTATION_LANDSCAPE && !ActivityMain.tabletSize) {
                expandVideoView(exoPlayerView);
                setViewVisibility(descriptionCard, false);
                setViewVisibility(mNavi, false);
                hideSystemUI();
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) exoPlayerView.getLayoutParams();
                expandVideoView(exoPlayerView);
                exoPlayerView.setLayoutParams(params);
            } else {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) exoPlayerView.getLayoutParams();
                exoPlayerView.setLayoutParams(params);

            }
        } else {
            // Hide video view
            setViewVisibility(exoPlayerView, false);
            setViewVisibility(andr, true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            poz = exoPlayer.getCurrentPosition();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        initializeMediaSession();
        releasePlayer();
        //Log.e(TAG,"14 onPause "+poz);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Log.e(TAG," 15 onStop"+poz);
        if (Util.SDK_INT > 23) {
            if (exoPlayer != null) {
                exoPlayer.stop();
                exoPlayer.release();
            }
        }
    }

    private void releasePlayer() {

        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }

        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
        //Log.e(TAG,"16 releaseplayer() "+poz);
    }
}