package com.twilio.video.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.twilio.video.app.ApiModals.UserObj;
import com.twilio.video.app.R;
import com.twilio.video.app.SkillItemResponse.Datum;
import com.twilio.video.app.subMainPages.SkillDetailsPage;

import java.util.List;

public class HostedSkillAdapter extends RecyclerView.Adapter<HostedSkillAdapter.HostedSkillAdapterViewHolder> {

    List<Datum> skillDatList;
    Context context;
    UserObj userObj;

    public HostedSkillAdapter(List<Datum> skillDatList, Context context,UserObj userObj) {
        this.skillDatList = skillDatList;
        this.context = context;
        this.userObj = userObj;
    }


    @NonNull
    @Override
    public HostedSkillAdapter.HostedSkillAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.new_skill_item,parent,false);
        return new HostedSkillAdapter.HostedSkillAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostedSkillAdapter.HostedSkillAdapterViewHolder holder, int position) {

        holder.tvSkillName.setText(skillDatList.get(position).getName());
        if(skillDatList.get(position).getFee().equalsIgnoreCase("0"))
            holder.tvfee.setText("Free Skill");
        else
            holder.tvfee.setText("INR : "+skillDatList.get(position).getFee());
        holder.tvmemeber.setText(String.valueOf(skillDatList.get(position).
              getFollowers_count())+" Member");
        holder.tvhost.setText(" Hosted By : "+skillDatList.get(position).getCreator().getName());

        if(skillDatList.get(position).getCoverPath()!=null)
        {
            Glide.with(context).load("http://nexgeno1.s3.us-east-2.amazonaws.com/public/uploads/covers/mini/"
                    +skillDatList.get(position).getCoverPath())
                    .into(holder.ivcover);
        }else {
            holder.ivcover.setImageResource(R.drawable.welcome_image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, SkillDetailsPage.class);
                i.putExtra("status","Created");
                i.putExtra("skillId",skillDatList.get(position).getId().toString());
                i.putExtra("memCount",String.valueOf(skillDatList.get(position).
                       getFollowers_count()));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return skillDatList.size();
    }

    public class HostedSkillAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvSkillName,tvfee,tvmemeber,tvhost;

        ImageView ivcover;
        public HostedSkillAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSkillName = itemView.findViewById(R.id.tv_skill_name_on_skill_item);
            tvfee = itemView.findViewById(R.id.tv_skill_fees_on_Skill_item);
            tvmemeber = itemView.findViewById(R.id.tv_skill_members_on_skill_item);
            tvhost = itemView.findViewById(R.id.tv_skill_host_on_skill_item);
            ivcover = itemView.findViewById(R.id.iv_skill_item_cover);
        }
    }
}

