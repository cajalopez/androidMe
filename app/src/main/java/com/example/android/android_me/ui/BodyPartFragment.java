package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.android.android_me.R;
import java.util.ArrayList;
import java.util.List;

public class BodyPartFragment extends Fragment {


    private final String TAG="BodyPartFragment";
    private final String selectedList="LIST";
    private final String selectedValue="SELECTED";

    private List<Integer> imagesResource=new ArrayList<>();
    private Integer selectedResource=null;

    public void setImagesResource(List<Integer> imagesResource) {
        this.imagesResource = imagesResource;
    }

    public void setSelectedResource(Integer selectedResource) {
        this.selectedResource = selectedResource;
    }

    public BodyPartFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);

        if(savedInstanceState!=null){
            imagesResource=savedInstanceState.getIntegerArrayList(selectedList);
            selectedResource=savedInstanceState.getInt(selectedValue);
        }

        if(selectedResource!=null && !imagesResource.isEmpty()){
            imageView.setImageResource(imagesResource.get(selectedResource));
        } else {
            Log.e(TAG,"Not selected image and not assign array of image");
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedResource<imagesResource.size()-1){
                    selectedResource++;
                    imageView.setImageResource(imagesResource.get(selectedResource));
                }else {
                    selectedResource=0;
                    imageView.setImageResource(imagesResource.get(selectedResource));
                }
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // TODO(1) Why do i have to parse the list
        Log.e(TAG,"Using on save instance state");
        outState.putIntegerArrayList(selectedList,(ArrayList<Integer>)imagesResource);
        outState.putInt(selectedValue,selectedResource);
    }
}
