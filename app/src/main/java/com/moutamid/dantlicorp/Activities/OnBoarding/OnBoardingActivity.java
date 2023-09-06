package com.moutamid.dantlicorp.Activities.OnBoarding;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.moutamid.dantlicorp.Activities.Authentication.LoginActivity;
import com.moutamid.dantlicorp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OnBoardingActivity extends AppCompatActivity {
    List<OnBoardingModel> listBoarding;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboarding);
        initViews();


        final LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                final View itemView = mLayoutInflater.inflate(R.layout.pager_row, container, false);
                CardView cvBtn = itemView.findViewById(R.id.cvv);
                Button btnGetStart = itemView.findViewById(R.id.btnGetStart);
                ImageView imageView = itemView.findViewById(R.id.imgMain);
                TextView txtTitle = itemView.findViewById(R.id.txtTitle);
                TextView txtMain = itemView.findViewById(R.id.txtMain);
                imageView.setImageResource(listBoarding.get(position).getVector());
                txtTitle.setText(listBoarding.get(position).getText());
                txtMain.setText(listBoarding.get(position).getTextMain());


                imageView.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.VISIBLE);

                imageView.clearAnimation();
                txtTitle.clearAnimation();
                imageView.setAnimation(null);


                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_to_top);
                imageView.setAnimation(animation);
                txtTitle.setAnimation(animation);

                btnGetStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences preferences = getSharedPreferences("Record", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("boarding_view", "yes");
                        editor.apply();
                        startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
                    }
                });
                cvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(position + 1);
                        imageView.setVisibility(View.GONE);
                        txtTitle.setVisibility(View.GONE);
                    }
                });

                if (position == 2) {
                    cvBtn.setVisibility(View.INVISIBLE);
                    btnGetStart.setVisibility(View.VISIBLE);
                }

                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        doBounceAnimation(cvBtn);
                    }
                }, 0, 5000);


                container.addView(itemView);
                return itemView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((RelativeLayout) object);
            }
        });

    }

    private void initViews() {
        listBoarding = new ArrayList<>();
        viewPager = findViewById(R.id.view_pager);
        prepareBoardingData();


        viewPager.setOnTouchListener((view, motionEvent) -> true);


    }

    private void doBounceAnimation(View targetView) {
        Interpolator interpolator = v -> {
            return getPowOut(v, 2);//Add getPowOut(v,3); for more up animation
        };
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationY", 0, 25, 0);
        animator.setInterpolator(interpolator);
        animator.setStartDelay(200);
        animator.setDuration(800);
        animator.setRepeatCount(2);

        runOnUiThread(() -> animator.start());

    }

    private float getPowOut(float elapsedTimeRate, double pow) {
        return (float) ((float) 1 - Math.pow(1 - elapsedTimeRate, pow));
    }

    void prepareBoardingData() {
        OnBoardingModel bm = new OnBoardingModel("The process of the delivery of products from the seller's warehouse to the customer's location", "Door to Door Delivery", R.drawable.boarding1);
        listBoarding.add(bm);
        bm = new OnBoardingModel("Stay updated on parcel status with our reliable parcel tracking platform", "Easy Tracking", R.drawable.boarding2);
        listBoarding.add(bm);
        bm = new OnBoardingModel("We help you to save your time and money.", "Price Comparison", R.drawable.boarding3);
        listBoarding.add(bm);
    }
}
