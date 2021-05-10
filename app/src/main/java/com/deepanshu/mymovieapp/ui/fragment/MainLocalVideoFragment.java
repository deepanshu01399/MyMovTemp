package com.deepanshu.mymovieapp.ui.fragment;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.adapter.CommanMovieAdapter;
import com.deepanshu.mymovieapp.ui.activity.LocalVideoPlayActvity;
import com.deepanshu.mymovieapp.ui.activity.LocalVideoPlayListActvity;
import com.deepanshu.mymovieapp.ui.module.LocalVideoModel;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.deepanshu.mymovieapp.util.StaticUtil.bytesIntoHumanReadable;


public  class MainLocalVideoFragment extends  BaseFragment implements View.OnClickListener {

    public static ArrayList<LocalVideoModel> videoArrayList;
    RecyclerView recyclerView;
    public static final int PERMISSION_READ = 0;
    private static String TAG = "LocalVideoFragment";


    public MainLocalVideoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //public LayoutInflater mInflater = LayoutInflater.from(getActivity()); // 1
        mInflater = getActivity().getLayoutInflater();
        //View theInflatedView = inflater.inflate(R.layout.your_layout, null); // 2 and 3


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_base, container, false);
        View view = inflater.inflate(R.layout.main_local_video_fragment,container,false);
        initView(view);
        setOnCLickListner();
        return  view;
    }

    private void setOnCLickListner() {
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        if (checkPermission()) {
            videoList();
        }

    }

    public void videoList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        videoArrayList = new ArrayList<>();
        getVideos();

    }
    //get video files from storage
    public void getVideos() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        //looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                //Uri data = FileProvider.getUriForFile(getContext(), "com.deepanshu.mymovieapp.fileprovider", new File()));
                String size = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                int thumb = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                String date_taken = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN));

                Log.e(TAG,"data:"+data);
                LocalVideoModel  videoModel  = new LocalVideoModel(title,date_taken,size,Uri.parse(data),thumb);
                videoArrayList.add(videoModel);

            } while (cursor.moveToNext());
        }

        CommanMovieAdapter adapter = new CommanMovieAdapter (getContext());
        recyclerView.setAdapter(adapter);

        adapter.setData(videoArrayList,false);
        adapter.setCallBack(R.layout.local_video_list_item, new CommanMovieAdapter.ICallBack() {
            @Override
            public void init(View view, Object object) {

            }

            @Override
            public void execute(View view, Object object) {
                LocalVideoModel localVideoModel =(LocalVideoModel)object;
                TextView textViewTitle = view.findViewById(R.id.title);
                textViewTitle.setText(localVideoModel.getVideoTitle());
                TextView textViewDuration = view.findViewById(R.id.duration);
                textViewDuration.setText(localVideoModel.getVideoDuration());
                TextView textVideoSize = view.findViewById(R.id.videoSize);
                Long videoSize = Long.parseLong(localVideoModel.getVideoSize());
                textVideoSize.setText(bytesIntoHumanReadable(videoSize));
                CircleImageView circleImageView = view.findViewById(R.id.localVideoThumbNail);
                circleImageView.setImageResource(localVideoModel.getThumbnail());
                //ImageView imageView = view.findViewById(R.id.imageview);
                //imageView.setImageResource(localVideoModel.getVideoUri());

            }

            @Override
            public void onClick(View view, Object object, int position) {
               /*
                LocalVideoModel localVideoModel = (LocalVideoModel)object;
                Intent intent = new Intent(getContext(), LocalVideoPlayActvity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("localVideoModel",localVideoModel);
                bundle.putInt("position", position);
                bundle.putParcelableArrayList("localPlayList",videoArrayList);
                intent.putExtras(bundle);
                startActivityForResult(intent,30,bundle);

               */
            }
        });


    }

    //time conversion
    public String timeConversion(long value) {
        String videoTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            videoTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            videoTime = String.format("%02d:%02d", mns, scs);
        }
        return videoTime;
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


    //used to replace the frament... from one fragemnt to another
    private void replaceFragment(BaseFragment fragment, FragmentManager fragmentManager, boolean isAddedToBackStack) {
        /*  fragmentManager.addOnBackStackChangedListener(this);*/
        FragmentTransaction fragTrans = fragmentManager.beginTransaction();
        fragTrans.replace(R.id.fragment_container, fragment, fragment.toString());
        if (isAddedToBackStack)
            fragTrans.addToBackStack(fragment.toString());
        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragTrans.commit();
        fragmentManager.executePendingTransactions();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

          }
    }

    @Override
    public void onPause() {
        super.onPause();
   }

    @Override
    public void onResume() {
        super.onResume();
    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case  PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {
                        videoList();
                    }
                }
            }
        }
    }

}