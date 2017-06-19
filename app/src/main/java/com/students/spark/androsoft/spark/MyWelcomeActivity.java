package com.students.spark.androsoft.spark;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.FragmentWelcomePage;
import com.stephentuso.welcome.ParallaxPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeConfiguration;
//import com.stephentuso.welcome.WelcomeBuilder;
//import com.stephentuso.welcome.ui.DoneButton;
import com.stephentuso.welcome.WelcomeActivity;
//import com.stephentuso.welcome.ui.WelcomeFragmentHolder;
//import com.stephentuso.welcome.util.WelcomeScreenConfiguration;

/**
 * Created by Sahaj on 5/28/2016.
 */
public class MyWelcomeActivity extends WelcomeActivity {
    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                //.theme(R.style.WelcomeScreenTheme_Light)
                // .theme(R.style.CustomWelcomeScreenTheme)
                .defaultTitleTypefacePath("Montserrat-Bold.ttf")
                .defaultHeaderTypefacePath("Montserrat-Bold.ttf")
                .page(new TitlePage(R.drawable.traffic_sign, "Welcome").background(R.color.SwipeScreen1))
                //.titlePage(, , R.color.SwipeScreen1)
                //.basicPage(, , , )
                .page(new BasicPage(R.drawable.ic_whatshot_black_24dp,
                        "Seamless",
                        "Connect to everyone Seamlessly.").background(R.color.red)
                )
                .page(new ParallaxPage(R.layout.welcome_glasses_parallax,
                        "Glasses",
                        "Say  Hi!  to your Lecturers.").background(R.color.violet)
                        .firstParallaxFactor(0.2f)
                        .lastParallaxFactor(0.2f)
                )
                .page(new ParallaxPage(R.layout.welcome_services_parallax,
                        "Services",
                        "Enjoy'n'Explore the campus.").background(R.color.Swipe_last)
                        .firstParallaxFactor(0.7f)
                        .lastParallaxFactor(0.2f)
                )
                //.parallaxPage(, , ,, 0.2f, 2f)
                // .parallaxPage(,,, , 0.2f, 2f)
                .page(new FragmentWelcomePage() {
                    @Override
                    protected android.support.v4.app.Fragment fragment() {
                        return new WelcomeProfileFillFragment();
                    }
                }.background(R.color.Swipe_last2))

                //.swipeToDismiss(true)
                .backButtonNavigatesPages(true)
                .canSkip(false)
                .useCustomDoneButton(true)
                .build();
    }





}
