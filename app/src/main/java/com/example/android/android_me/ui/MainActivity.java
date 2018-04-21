package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.onImageSelectedCallback{


    private int headIndex;
    private int legIndex;
    private int bodyIndex;
    private final String TAG="MainActivity";
    private boolean mTwopane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Main activity have a reference to static fragments, that means
        // this fragments is instance by the XML.
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.android_me_linear_layout) != null){

            mTwopane = true;
            Button nextButon = (Button) findViewById(R.id.next_button);
            nextButon.setVisibility(View.GONE);

            GridView gridView = (GridView) findViewById(R.id.images_grid_view);
            gridView.setNumColumns(2);

            if(savedInstanceState==null) {

                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setImagesResource(AndroidImageAssets.getHeads());
                // GETTING THE INTENT FROM THE CALLBACK MainActivity
                int headIndex = getIntent().getIntExtra("headIndex",0);
                headFragment.setSelectedResource(headIndex);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImagesResource(AndroidImageAssets.getBodies());
                int bodyIndex = getIntent().getIntExtra("bodyIndex",0);
                bodyFragment.setSelectedResource(bodyIndex);
                fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImagesResource(AndroidImageAssets.getLegs());
                int legIndex = getIntent().getIntExtra("legIndex",0);
                legFragment.setSelectedResource(legIndex);
                fragmentManager.beginTransaction()
                        .add(R.id.leg_container, legFragment)
                        .commit();

            }


        }else {
            mTwopane = false;
        }

    }

    @Override
    public void imagePosition(int position) {


        // Funny math logic to retrieve result from the selected fragment
        int bodyPartNumber = position / 12;
        Log.e(TAG,String.valueOf(bodyPartNumber));
        int listIndex = position - 12*bodyPartNumber;

        if(mTwopane){

            BodyPartFragment newFragment = new BodyPartFragment();
            switch (bodyPartNumber){
                case 0:
                    newFragment.setImagesResource(AndroidImageAssets.getHeads());
                    newFragment.setSelectedResource(listIndex);
                    getSupportFragmentManager().beginTransaction().replace(R.id.head_container,newFragment).commit();
                    break;
                case 1:
                    newFragment.setImagesResource(AndroidImageAssets.getBodies());
                    newFragment.setSelectedResource(listIndex);
                    getSupportFragmentManager().beginTransaction().replace(R.id.body_container,newFragment).commit();
                    break;
                case 2:
                    newFragment.setImagesResource(AndroidImageAssets.getLegs());
                    newFragment.setSelectedResource(listIndex);
                    getSupportFragmentManager().beginTransaction().replace(R.id.leg_container,newFragment).commit();
                    break;
            }

        } else {

            switch (bodyPartNumber) {
                case 0: headIndex = listIndex;
                    break;
                case 1: bodyIndex = listIndex;
                    break;
                case 2: legIndex = listIndex;
                    break;
                default: break;
            }


            final Intent intent = new Intent(this,AndroidMeActivity.class);

            intent.putExtra("headIndex",headIndex);
            intent.putExtra("bodyIndex",bodyIndex);
            intent.putExtra("legIndex",legIndex);

            Button next = (Button) findViewById(R.id.next_button);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });

        }
    }
}
