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

public class FragmentStep extends Fragment implements ExoPlayer.EventListener {

    public static TextView lepes;
    public static String videoString;

    public void setVideoString(String videoString) {
        FragmentStep.videoString = videoString;
    }
String TAG = "ááááááá";
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

    public FragmentStep() {}

    public void videoLoad()
    {
        if(step != null)
        {
            if(!ActivityMain.tabletSize){
                video = step.getVideoUrl();}
            else{video = videoString;}
        }
        else
        {
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
            } else
                {
                Log.e("ggggggggggg", ""+video);

                //video = videoString;

                    //initializeMediaSession();
                    //initializePlayer(Uri.parse(video));
                     ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) exoPlayerView.getLayoutParams();
                    exoPlayerView.setLayoutParams(params);

//                else
//                 {
//                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) exoPlayerView.getLayoutParams();
//                exoPlayerView.setLayoutParams(params);
//                }
            }
        } else {
            // Hide video view
            setViewVisibility(exoPlayerView, false);
            setViewVisibility(andr, true);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {


                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

   switch (item.getItemId())
                    {

                        case R.id.navigation_left:
                            BottomNavigationViewHelper.disableShiftMode(mNavi);
                            if(tempSelection > 0 || telSel > 0)
                            {
                                if(!ActivityMain.tabletSize)
                                {
                                    telSel = telSel-1;
                                    step = FragmentDetail.lepesek.get(telSel);
                                    lepes.setText(FragmentDetail.lepesek.get(telSel).getDescription());
                                    releasePlayer();
                                    videoLoad();
                                }
                                else
                                {
                                    tempSelection = tempSelection-1;
                                    lepes.setText(FragmentDetail.lepesek.get(tempSelection).getDescription());
                                    video = FragmentDetail.lepesek.get(tempSelection).getVideoUrl();

                                    if(video!=null && !video.equals(""))
                                    {
                                        setViewVisibility(exoPlayerView,true);
                                        setViewVisibility(andr, false);
                                        releasePlayer();
                                        initializePlayer(Uri.parse(video));
                                        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) exoPlayerView.getLayoutParams();
                                        exoPlayerView.setLayoutParams(params);
                                    }
                                    else
                                    {
                                        setViewVisibility(exoPlayerView, false);
                                        setViewVisibility(andr, true);
                                    }
                                }
                            }
                            return true;
                        case R.id.navigation_right:
                            BottomNavigationViewHelper.disableShiftMode(mNavi);
                            if(tempSelection < FragmentDetail.lepesek.size()-1 && telSel < FragmentDetail.lepesek.size()) {
                                if (!ActivityMain.tabletSize) {
                                    telSel = telSel + 1;
                                    step = FragmentDetail.lepesek.get(telSel);
                                    lepes.setText(step.getDescription());
                                    releasePlayer();
                                    videoLoad();
                                } else {
                                    tempSelection = tempSelection + 1;
                                    Log.e(TAG, tempSelection + "");
                                    lepes.setText(FragmentDetail.lepesek.get(tempSelection).getDescription());
                                    video = FragmentDetail.lepesek.get(tempSelection).getVideoUrl();
                                    Log.e(TAG, "" + video);
                                    if (!video.equals(null) && !video.equals("")) {
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

   /* @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(videoAvailableFlag) {
            playbackPosition = exoPlayer.getCurrentPosition();
        }else{
            playbackPosition=0;
        }
        if(exoPlayer!=null) {
            shouldAutoPlay = exoPlayer.getPlayWhenReady();
        }
        outState.putBoolean(PLAY_STATE_RESTORE,shouldAutoPlay);
        outState.putLong(PLAYBACK_POSITION, playbackPosition);
        outState.putInt(SELECTION_MADE_ON_STATE,tempSelection);
        outState.putBoolean(VIDEO_AVAIL,videoAvailableFlag);
        step = FragmentDetail.lepesek.get(FragmentDetail.currentSelection);
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_single_step,container, false);
        lepes = (TextView) view.findViewById(R.id.recipe_step);
        lay = (LinearLayout) view.findViewById(R.id.reszletes_lepes_layout);
        mNavi= (BottomNavigationView) view.findViewById(R.id.navigation);
        try {
            if(ActivityMain.tabletSize){step = FragmentDetail.lepesek.get(tempSelection);}
            else{step = FragmentDetail.lepesek.get(telSel);}
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        exoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.recipe_step_video);
        descriptionCard = (CardView) view.findViewById(R.id.recipe_step_desc_card);
        mNavi.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //tempSelection = FragmentDetail.currentSelection;
        andr = (ImageView) view.findViewById(R.id.android_eats_apple_image);

        BottomNavigationViewHelper.disableShiftMode(mNavi);
        Log.e("FragmentStep","telSel"+telSel);

        setHasOptionsMenu(true);

        if(savedInstanceState != null) {

            Log.e("tabSize?", "" + ActivityMain.tabletSize);
            if (!ActivityMain.tabletSize)
            {
                lepes.setText(step.getDescription());
                videoLoad();
            }
            //else if(ActivityMain.tabletSize){Log.e("onCreate",video+"");}
            else
            {


            }

        }
        try {
            if(ActivityMain.tabletSize) {
                lepes.setText(step.getDescription());

            }
            else{lepes.setText(step.getDescription());}
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return view;
    }//onCreate vége


    private void setViewVisibility(View view, boolean show) {
        if (show) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void initializePlayer(Uri mediaUri) {

            exoPlayerView.requestFocus();
            TrackSelector trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity().getApplicationContext(), trackSelector);
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.addListener(this);

           String userAgent = Util.getUserAgent(getContext(), "StepVideo");
           MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                  getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
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
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
/*            //First Hide other objects (listview or recyclerview), better hide them using Gone.
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) exoPlayerView.getLayoutParams();
            params.width=params.MATCH_PARENT;
            params.height=params.MATCH_PARENT;
            exoPlayerView.setLayoutParams(params);*/
            expandVideoView(exoPlayerView);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //unhide your objects here.
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) exoPlayerView.getLayoutParams();
            params.width=params.MATCH_PARENT;
            params.height=600;
            exoPlayerView.setLayoutParams(params);
        }
    }

    // FROM EXOPLAYER CODELABS

    private void initializeMediaSession() {

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
        exoPlayer.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        exoPlayer.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    //  https://developer.android.com/training/system-ui/immersive.html
    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) { }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) { }

    @Override
    public void onLoadingChanged(boolean isLoading) { }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState)
    {
/*        if(exoPlayer != null) {
            if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
                stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, exoPlayer.getCurrentPosition(), 1f);
            } else if ((playbackState == ExoPlayer.STATE_READY)) {
                stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, exoPlayer.getCurrentPosition(), 1f);
            }
        }
       mediaSession.setPlaybackState(stateBuilder.build());*/
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) { }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) { }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onResume() {
        super.onResume();
        videoLoad();
    }
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }}


}


