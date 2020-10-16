package com.twilio.video.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.danikula.videocache.HttpProxyCacheServer;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.twilio.video.app.ApiModals.MakeClassResponse;
import com.twilio.video.app.ApiModals.PostLikeResponse;
import com.twilio.video.app.HomePostModal.Comment;
import com.twilio.video.app.HomePostModal.Datum;
import com.twilio.video.app.HomePostModal.Like;
import com.twilio.video.app.MainPages.OtherUserProfile;
import com.twilio.video.app.R;
import com.twilio.video.app.RetrifitClient;
import com.twilio.video.app.UpdatePostResponse;
import com.twilio.video.app.subMainPages.DetailedVidView;
import com.twilio.video.app.util.TimeService;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HomePostsAdapter extends RecyclerView.Adapter<HomePostsAdapter.HomePostAdapterViewHolder> {

    List<Datum> postList;
    private Context context;
    int userId;
    PopupWindow mpopup;
    String token;


    public HomePostsAdapter(List<Datum> postList, Context context, int userId, String token) {
        this.postList = postList;
        this.context = context;
        this.userId = userId;
        this.token = token;
    }


    public List<Datum> getPostList() {
        return postList;
    }

    public void setPostList(List<Datum> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }


    @Override
    public void onViewAttachedToWindow(@NonNull HomePostAdapterViewHolder holder) {

        if (holder.videoView.getVisibility() == View.VISIBLE) {
            holder.progressBar.setVisibility(View.VISIBLE);
            Sprite chasingDots = new ChasingDots();
            chasingDots.setColor(Color.parseColor("#2073CC"));
          //  chasingDots.setScale(0.7f);
            holder.progressBar.setIndeterminateDrawable(chasingDots);
            Uri video = Uri.parse(holder.payload.getText().toString().trim());
            HttpProxyCacheServer proxy = new HttpProxyCacheServer(context);
            String proxyUrl = proxy.getProxyUrl(holder.payload.getText().toString().trim());
            holder.videoView.setVideoPath(proxyUrl);
//            holder.videoView.setVideoURI(video);

            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.ivPlayPouse.setTag("Playing");
                    holder.videoView.start();
                }
            });
        }
        super.onViewAttachedToWindow(holder);
    }


    @NonNull
    @Override
    public HomePostsAdapter.HomePostAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post_item, parent, false);
        return new HomePostAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePostAdapterViewHolder holder, int position) {

        Glide.with(context).load("http://nexgeno1.s3.us-east-2.amazonaws.com/public/uploads/profile_photos/" + postList.get(position).getProfilePath()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.INVISIBLE);

                return false;
            }
        }).into(holder.userProfile);
        holder.userName.setText(postList.get(position).getName());
        try {
            long time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(postList.get(position).getCreatedAt()).getTime();
            holder.timesAgo.setText(new TimeService().getTimeAgo(time, System.currentTimeMillis()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Integer> likeeduser = new ArrayList<>();
        likeeduser = getLikedUSerIds(postList.get(position).getLikes());

        if (likeeduser.contains(userId)) {
            holder.likeView.setImageResource(R.drawable.ic_baseline_favorite_24);
            holder.likeView.setTag("Liked");
        }

        if (userId == postList.get(position).getUserId()) {
            holder.menuImage.setVisibility(View.VISIBLE);
        } else {
            holder.menuImage.setVisibility(View.GONE);
        }

        holder.likeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.likeView.getTag().equals("Liked")) {
                    holder.likeView.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    holder.likeView.setTag("UnLiked");
                    int likesNo = Integer.parseInt(holder.noOfLikes.getText().toString());
                    likesNo--;
                    holder.noOfLikes.setText(String.valueOf(likesNo));
                    likePostByApi(postList.get(position).getPostId().toString());

                } else {
                    holder.likeView.setImageResource(R.drawable.ic_baseline_favorite_24);
                    holder.likeView.setTag("Liked");
                    int likesNo = Integer.parseInt(holder.noOfLikes.getText().toString());
                    likesNo++;
                    holder.noOfLikes.setText(String.valueOf(likesNo));
                    likePostByApi(postList.get(position).getPostId().toString());

                }
            }
        });

        // pop Up Menu To delete And update Post
        holder.menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO PopUpMenu
                PopupMenu popup = new PopupMenu(context, holder.menuImage);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_on_post);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.update_post:
                                showUpdatePostpopup(postList.get(position));
                                //Toast.makeText(context, "Update Post", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.delete_post:
                                //Toast.makeText(context, "Delete Post", Toast.LENGTH_SHORT).show();
                                deletePost(postList.get(position), position);
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
            }
        });

        holder.caption.setText(postList.get(position).getContent());
        holder.caption2.setText(postList.get(position).getContent());


//            if (postList.get(position).getHasImage() == 0 && postList.get(position).getHasVideo() == 0 && postList.get(position).getYoutubeLink() == null){
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    holder.caption.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
//                }else {
//                    holder.caption.setTextSize(17f);
//                }
//            }
//            else if(postList.get(position).getContent().length()<350)
//            {
//                holder.caption.setTextSize(14f);
//            }else {
//                if(postList.get(position).getContent().length()>800)
//                {
//                    holder.caption.setTextSize(8f);
//                }else {
//                    holder.caption.setTextSize(110f);
//                }
//        }

        holder.noOfLikes.setText(String.valueOf(postList.get(position).getLikes().size()));
        holder.noOfComment2.setText(String.valueOf(postList.get(position).getComments().size()));
        if (postList.get(position).getHasImage() == 1) {
            holder.caption2.setVisibility(View.VISIBLE);
            holder.caption.setVisibility(View.GONE);
            holder.mediaView.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.GONE);
            Glide.with(context).load("http://nexgeno1.s3.us-east-2.amazonaws.com/public/uploads/posts/" + postList.get(position).getDUploadedFiles().get(0).getFilePath()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.mediaView);
        }

        if (postList.get(position).getHasVideo() == 1) {
            holder.caption2.setVisibility(View.VISIBLE);
            holder.caption.setVisibility(View.GONE);
            HttpProxyCacheServer proxy = new HttpProxyCacheServer(context);
//            if (postList.get(position+1).getHasVideo() == 1){
//            HttpProxyCacheServer proxy = new HttpProxyCacheServer(context);
//
//                Log.d("dddddddddddddd","http://nexgeno1.s3.us-east-2.amazonaws.com/public/uploads/posts/"
//                        +postList.get(position+1).getDUploadedFiles().get(0).getFilePath().trim());
//
////                InputStream inputStream = null;
////                try {
////                    inputStream = new URL(proxy.getProxyUrl("http://nexgeno1.s3.us-east-2.amazonaws.com/public/uploads/posts/"
////                            +postList.get(position+1).getDUploadedFiles().get(0).getFilePath().trim())).openStream();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////                try {
////                    int bufferSize = 1024;
////                    byte[] buffer = new byte[bufferSize];
////                    int length = 0;
////                    while ((length = inputStream.read(buffer)) != -1) {
////                        //nothing to do
////                    }
////                } catch(IOException e){
////                    e.printStackTrace();
////                }
//            }
            String vidUrl = "http://nexgeno1.s3.us-east-2.amazonaws.com/public/uploads/posts/"
                    + postList.get(position).getDUploadedFiles().get(0).getFilePath();
            holder.ivPlayImg.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.VISIBLE);
            MediaController mc = new MediaController(context);
            mc.setAnchorView(holder.videoView);
            mc.setMediaPlayer(holder.videoView);
            // holder.videoView.setMediaController(mc);
            holder.payload.setText(vidUrl);
        }

        if (postList.get(position).getYoutubeLink() != null) {
            holder.progressBar.setVisibility(View.GONE);
            holder.caption2.setVisibility(View.VISIBLE);
            holder.caption.setVisibility(View.GONE);
            holder.ytVidView.setVisibility(View.VISIBLE);
            holder.ytVidView.addYouTubePlayerListener(new YouTubePlayerListener() {
                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(postList.get(position).getYoutubeLink().toString(),
                            0);
                }

                @Override
                public void onStateChange(@NotNull YouTubePlayer youTubePlayer, PlayerConstants.@NotNull PlayerState playerState) {

                }

                @Override
                public void onPlaybackQualityChange(@NotNull YouTubePlayer youTubePlayer, PlayerConstants.@NotNull PlaybackQuality playbackQuality) {

                }

                @Override
                public void onPlaybackRateChange(@NotNull YouTubePlayer youTubePlayer, PlayerConstants.@NotNull PlaybackRate playbackRate) {

                }

                @Override
                public void onError(@NotNull YouTubePlayer youTubePlayer, PlayerConstants.@NotNull PlayerError playerError) {

                }

                @Override
                public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoLoadedFraction(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String s) {

                }

                @Override
                public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {

                }
            });
            holder.payload.setText(postList.get(position).getYoutubeLink().toString());

        }

        //Set FontSize if only text
        if (postList.get(position).getContent().length() < 1) {
            holder.caption2.setVisibility(View.GONE);
            holder.caption.setVisibility(View.GONE);
        }

        if (postList.get(position).getHasImage() == 0 && postList.get(position).getHasVideo() == 0
                && postList.get(position).getYoutubeLink() == null) {
            //SteTextSize

            if (postList.get(position).getContent().length() < 5) {
                holder.caption.setTextSize(40f);
            } else {
                if (postList.get(position).getContent().length() >= 5 && postList.get(position).getContent().length() <= 20) {
                    holder.caption.setTextSize(35f);
                } else {
                    if (postList.get(position).getContent().length() >= 20 && postList.get(position).getContent().length() <= 200) {
                        holder.caption.setTextSize(25f);
                    } else {
                        if (postList.get(position).getContent().length() >= 200 && postList.get(position).getContent().length() <= 400) {
                            holder.caption.setTextSize(20f);
                        } else {
                            if (postList.get(position).getContent().length() >= 400 && postList.get(position).getContent().length() <= 650) {
                                holder.caption.setTextSize(15f);
                            } else {
                                if (postList.get(position).getContent().length() >= 650 && postList.get(position).getContent().length() <= 900) {
                                    holder.caption.setTextSize(14f);
                                }else {
                                    if (postList.get(position).getContent().length() > 900)
                                    {
                                        holder.caption.setTextSize(11f);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else {

        }

        holder.ivPlayImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVidPopup("http://nexgeno1.s3.us-east-2.amazonaws.com/public/uploads/posts/"
                        + postList.get(position).getDUploadedFiles().get(0).getFilePath());
            }
        });

        holder.relMainView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
              if(holder.videoView.getVisibility()==View.VISIBLE)
              {
                  if(holder.videoView.isPlaying())
                  {
                      holder.ivPlayPouse.setImageResource(R.drawable.exomedia_ic_pause_white);
                      holder.ivPlayPouse.setTag("Playing");
                  }else {
                      holder.ivPlayPouse.setImageResource(R.drawable.ic_play_white_24px);
                      holder.ivPlayPouse.setTag("Stopped");
                  }
                  if(holder.ivPlayPouse.getVisibility()==View.GONE)
                  {
                      holder.ivPlayPouse.setVisibility(View.VISIBLE);
                  }else {
                      holder.ivPlayPouse.setVisibility(View.GONE);
                  }
                  //Add Mute Logic

              }else {

              }
                return true;
            }
        });

        holder.ivPlayPouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.ivPlayPouse.getTag().equals("Playing"))
                {
                    holder.videoView.pause();
                    holder.ivPlayPouse.setImageResource(R.drawable.ic_play_white_24px);
                    holder.ivPlayPouse.setTag("Paused");
                }else {
                    holder.videoView.start();
                    holder.ivPlayPouse.setImageResource(R.drawable.exomedia_ic_pause_white);
                    holder.ivPlayPouse.setTag("Playing");
                }
            }
        });

        holder.tvSeeComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentOpoup(postList.get(position).getComments(), position);
            }
        });

        holder.mediaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popUpView = inflater.inflate(R.layout.detailed_media_view_layout,
                        null); // inflating popup layout
                mpopup = new PopupWindow(popUpView, ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, true);
                ImageView image = popUpView.findViewById(R.id.iv_detailed_image);
                ImageView ivCross = popUpView.findViewById(R.id.iv_cancle_poup);
                ivCross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mpopup.dismiss();
                    }
                });

                Glide.with(context).load("http://nexgeno1.s3.us-east-2.amazonaws.com/public/uploads/posts/" + postList.get(position).getDUploadedFiles().get(0).getFilePath()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(image);

                // Creation of popup
                mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
                mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);

            }
        });
        holder.linlayuserNameanstimesHao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navToCreator(postList.get(position));
            }
        });

    }


    private void showVidPopup(String filePath) {

        Intent i = new Intent(
                context, DetailedVidView.class
        );
        i.putExtra("vid_file", filePath);
        context.startActivity(i);

    }

    private void likePostByApi(String postId) {

        Call<PostLikeResponse> call = RetrifitClient
                .getInstance().getPostApi().likePost(token, postId);

        call.enqueue(new Callback<PostLikeResponse>() {
            @Override
            public void onResponse(Call<PostLikeResponse> call, Response<PostLikeResponse> response) {

            }

            @Override
            public void onFailure(Call<PostLikeResponse> call, Throwable t) {

            }
        });
    }

    private void navToCreator(Datum postData) {

        Intent i = new Intent(context, OtherUserProfile.class);
        i.putExtra("token", token);
        i.putExtra("other_user_id", postData.getUserId().toString());
        context.startActivity(i);

    }

//    public  Bitmap retriveVideoFrameFromVideo(String videoPath)throws Throwable
//    {
//        Bitmap bitmap = null;
//        MediaMetadataRetriever mediaMetadataRetriever = null;
//        try
//        {
//            mediaMetadataRetriever = new MediaMetadataRetriever();
//            if (Build.VERSION.SDK_INT >= 14)
//                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
//            else
//                mediaMetadataRetriever.setDataSource(videoPath);
//            //   mediaMetadataRetriever.setDataSource(videoPath);
//            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)"+ e.getMessage());
//        }
//        finally
//        {
//            if (mediaMetadataRetriever != null)
//            {
//                mediaMetadataRetriever.release();
//            }
//        }
//        return bitmap;
//    }

    private void showCommentOpoup(List<Comment> comments, int position) {

        // ivSelectedImage.setImageURI(Uri.fromFile(coverImage));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.comments_popup_layout,
                null); // inflating popup layout
        mpopup = new PopupWindow(popUpView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        ImageView ivCross = popUpView.findViewById(R.id.iv_cancle_comment_popup);
        ImageView ivSend = popUpView.findViewById(R.id.iv_Send_comment);
        EditText etComment = popUpView.findViewById(R.id.et_comment_on_popup);
        RecyclerView recComments = popUpView.findViewById(R.id.rec_view_comments_in_popup);

        recComments.setLayoutManager(new LinearLayoutManager(context));
        recComments.setAdapter(new CommentsAdapter(comments, context, userId));


        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpopup.dismiss();
            }
        });

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etComment.getText().toString().isEmpty()) {

                } else {
                    hideKeybaord(v);
                    makeComment(etComment.getText().toString(), postList.get(position).getPostId().toString());
                }
            }
        });

        // Creation of popup
        mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
        mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    private void makeComment(String comment, String id) {
        Call<MakeClassResponse> call = RetrifitClient.getInstance()
                .getPostApi().addComment(token, id, comment);

        call.enqueue(new Callback<MakeClassResponse>() {
            @Override
            public void onResponse(Call<MakeClassResponse> call, Response<MakeClassResponse> response) {
                Log.d("Response>> ", response.raw().toString());
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        mpopup.dismiss();
                        Toast.makeText(context, "Comment Added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Can Not make Comment", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MakeClassResponse> call, Throwable t) {

            }
        });
    }

    private void deletePost(Datum datum, int position) {

        Call<MakeClassResponse> call = RetrifitClient.getInstance()
                .getPostApi().deletePost(token, datum.getId().toString());
        call.enqueue(new Callback<MakeClassResponse>() {
            @Override
            public void onResponse(Call<MakeClassResponse> call, Response<MakeClassResponse> response) {
                Log.d("Tag>> ", response.raw().toString());
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Toast.makeText(context, "Post Deleted", Toast.LENGTH_SHORT).show();
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, "Can Not Delete Post", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MakeClassResponse> call, Throwable t) {

            }
        });
    }

    private void showUpdatePostpopup(Datum datum) {

        // ivSelectedImage.setImageURI(Uri.fromFile(coverImage));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.update_post_layout,
                null); // inflating popup layout
        mpopup = new PopupWindow(popUpView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        ImageView image = popUpView.findViewById(R.id.iv_update_post);
        EditText etCaption = popUpView.findViewById(R.id.et_caption_on_update_post);
        if (datum.getContent() != null) {
            etCaption.setText(datum.getContent());
        }
        ImageView video = popUpView.findViewById(R.id.vv_update_post);
        YouTubePlayerView ytVid = popUpView.findViewById(R.id.ytVv_update_post);
        ImageView ivCross = popUpView.findViewById(R.id.iv_cancle_update_post);
        Button uploadPic = popUpView.findViewById(R.id.btn_update_post);

        if (datum.getHasImage() == 1) {
            image.setVisibility(View.VISIBLE);
            Glide.with(context).load("http://nexgeno1.s3.us-east-2.amazonaws.com/public/uploads/posts/" + datum.getDUploadedFiles().get(0).getFilePath()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    //holder.progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    //holder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(image);
        }
        if (datum.getHasVideo() == 1) {
            video.setVisibility(View.VISIBLE);
//            video.setVideoURI(Uri.parse("https://www.virtualskill.in/storage/uploads/posts/"
//                    + datum.getDUploadedFiles().get(0).getFilePath()));
//            video.setMediaController(new MediaController(context));

        }
        if (datum.getYoutubeLink() != null) {
            ytVid.setVisibility(View.VISIBLE);

            ytVid.initializeWithWebUi(new YouTubePlayerListener() {
                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(datum.getYoutubeLink().toString(), 0);
                }

                @Override
                public void onStateChange(@NotNull YouTubePlayer youTubePlayer, PlayerConstants.@NotNull PlayerState playerState) {

                }

                @Override
                public void onPlaybackQualityChange(@NotNull YouTubePlayer youTubePlayer, PlayerConstants.@NotNull PlaybackQuality playbackQuality) {

                }

                @Override
                public void onPlaybackRateChange(@NotNull YouTubePlayer youTubePlayer, PlayerConstants.@NotNull PlaybackRate playbackRate) {

                }

                @Override
                public void onError(@NotNull YouTubePlayer youTubePlayer, PlayerConstants.@NotNull PlayerError playerError) {

                }

                @Override
                public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoLoadedFraction(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String s) {

                }

                @Override
                public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {

                }
            }, true);

        }

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpopup.dismiss();
            }
        });

        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadePost(etCaption.getText().toString(), datum.getId().toString());
            }
        });


        // Creation of popup
        mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
        mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);

    }

    private void uploadePost(String caption, String postid) {

        Call<UpdatePostResponse> call = RetrifitClient.getInstance()
                .getPostApi().updatePost(token, postid, caption);

        call.enqueue(new Callback<UpdatePostResponse>() {
            @Override
            public void onResponse(Call<UpdatePostResponse> call, Response<UpdatePostResponse> response) {
                Log.d("Update Post Response>>", response.raw().toString());
                if (response.body() != null) {
                    Toast.makeText(context, " Updated Post", Toast.LENGTH_SHORT).show();
                    mpopup.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UpdatePostResponse> call, Throwable t) {

                Log.d("Errror Updation>>", t.toString());

            }
        });

    }

    private List<Integer> getLikedUSerIds(List<Like> likes) {
        List<Integer> userLikedIdLists = new ArrayList<>();
        for (Like like : likes
        ) {
            userLikedIdLists.add(like.getLikeUserId());
        }
        return userLikedIdLists;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }

    class HomePostAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView userName, timesAgo, noOfLikes, noOfComment2, caption, caption2, payload;
        ImageView tvSeeComments;
        CircleImageView userProfile;
        ProgressBar progressBar;
        ImageView mediaView, likeView, menuImage;
        // Makeing iv for ThumbNail
        ImageView ivPlayImg,ivPlayPouse,ivmuteUnMute;
        VideoView videoView;
        YouTubePlayerView ytVidView;
        LinearLayout linlayuserNameanstimesHao;
        RelativeLayout relMainView;

        public HomePostAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tv_user_name_on_post);
            timesAgo = itemView.findViewById(R.id.tv_times_ago_post);
            noOfLikes = itemView.findViewById(R.id.no_of_likes);
            noOfComment2 = itemView.findViewById(R.id.no_of_comments2);
            caption = itemView.findViewById(R.id.tv_caption_on_post);
            userProfile = itemView.findViewById(R.id.profile_pic_on_post);
            progressBar = itemView.findViewById(R.id.pb_load_video_for_post);
            mediaView = itemView.findViewById(R.id.iv_on_post);
            tvSeeComments = itemView.findViewById(R.id.tv_see_comments);
            likeView = itemView.findViewById(R.id.iv_like);
            menuImage = itemView.findViewById(R.id.iv_post_menu);
            videoView = itemView.findViewById(R.id.vv_on_post);
            ytVidView = itemView.findViewById(R.id.ytVv_on_post);
            linlayuserNameanstimesHao = itemView.findViewById(R.id.lin_lay_u_name_on_post_item);
            ivPlayImg = itemView.findViewById(R.id.iv_play_img);
            payload = itemView.findViewById(R.id.tv_as_payload);
            caption2 = itemView.findViewById(R.id.tv_caption_on_post2);
            ivPlayPouse = itemView.findViewById(R.id.iv_play_pouse);
            relMainView = itemView.findViewById(R.id.rel_main_view_of_post);
            ivmuteUnMute = itemView.findViewById(R.id.iv_mute_un_mute);
        }
    }
}


