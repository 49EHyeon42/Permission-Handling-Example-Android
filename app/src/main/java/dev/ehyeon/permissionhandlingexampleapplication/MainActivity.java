package dev.ehyeon.permissionhandlingexampleapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected Fragment permissionGrantedFragment;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionGrantedFragment = new PermissionGrantedFragment();
        Fragment permissionDeniedFragment = new PermissionDeniedFragment();

        fragments = new ArrayList<>();
        fragments.add(permissionGrantedFragment);
        fragments.add(permissionDeniedFragment);

        showFragment(permissionDeniedFragment);
    }

    protected void showFragment(Fragment targetFragment) {
        for (Fragment fragment : fragments) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (targetFragment == fragment) {
                if (!fragment.isAdded()) {
                    fragmentTransaction.add(R.id.fragment_container, fragment);
                }

                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.hide(fragment);
            }

            fragmentTransaction.commit();
        }
    }
}
