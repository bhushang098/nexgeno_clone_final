package com.twilio.video.app.subMainPages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.twilio.video.app.JoinedClassResponse.Datum;
import com.twilio.video.app.JoinedClassResponse.JoinedClassRespones;
import com.twilio.video.app.R;
import com.twilio.video.app.RetrifitClient;
import com.twilio.video.app.adapter.JoinedClassAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinedClasses#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinedClasses extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recJoinedClasses;
   ShimmerFrameLayout shimmerFrameLayout;
    private List<Datum> classesDataList = new ArrayList<>();
    TextView tvEmptyStatus;
    String token;
    SwipeRefreshLayout refreshLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JoinedClasses() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinedClasses.
     */
    // TODO: Rename and change types and number of parameters
    public static JoinedClasses newInstance(String param1, String param2) {
        JoinedClasses fragment = new JoinedClasses();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_joined_classes, null);
        recJoinedClasses = root.findViewById(R.id.rec_view_joined_classes);
        refreshLayout = root.findViewById(R.id.srl_joined_classes);
       shimmerFrameLayout = root.findViewById(R.id.sh_v_joined_classes_page);
       shimmerFrameLayout.startShimmerAnimation();
        tvEmptyStatus = root.findViewById(R.id.tv_joined_class_item_not_available);

        SharedPreferences settings = getContext().getSharedPreferences("login_preferences",
                Context.MODE_PRIVATE);

        token = settings.getString("token","");
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                classesDataList.clear();
                loadJoinedClasses(token);
            }
        });
        loadJoinedClasses(token);
        return root;
    }

    private void loadJoinedClasses(String token){
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        Call<JoinedClassRespones> call = RetrifitClient.getInstance()
                .getClassesApi().getJoinedClasses(token);
        call.enqueue(new Callback<JoinedClassRespones>() {
            @Override
            public void onResponse(Call<JoinedClassRespones> call, Response<JoinedClassRespones> response) {
                Log.d("REsponse>>>",response.raw().toString());
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                try{
                    if(response.body() == null){
                        Log.d("Error>>", response.errorBody().string());

                        //refreshLayout.setRefreshing(false);
                    }else {
                        Log.d("Class Response>>>>>>",response.body().toString());
                        classesDataList = response.body().getData();
                        if(classesDataList.size()>0)
                        {
                            recJoinedClasses.setLayoutManager(new GridLayoutManager(getContext(),2));
                            recJoinedClasses.setAdapter(new JoinedClassAdapter(classesDataList,getContext()));
                        }else {
                            tvEmptyStatus.setVisibility(View.VISIBLE);
                        }

                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        //refreshLayout.setRefreshing(false);
                    }


                }catch (Exception e){
                    e.printStackTrace();
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                    //refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<JoinedClassRespones> call, Throwable t) {
                // Toast.makeText(AvailableClasses.this, "Error ", Toast.LENGTH_SHORT).show();
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                Log.d("Exception >>>", t.toString());
                //refreshLayout.setRefreshing(false);
            }
        });
    }
}