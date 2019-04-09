package digitalcreative.web.id.wbmobile_user.view.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import digitalcreative.web.id.wbmobile_user.R;
import digitalcreative.web.id.wbmobile_user.view.fragment.BantuanFragment;
import digitalcreative.web.id.wbmobile_user.view.fragment.HomeFragment;
import digitalcreative.web.id.wbmobile_user.view.fragment.ModulFragment;
import digitalcreative.web.id.wbmobile_user.view.fragment.PaketFragment;

public class BaseActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        goToMenuUtama();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setBottomNavigationView(bottomNavigationView);
    }

    private void setBottomNavigationView(BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.home :
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.paket :
                        selectedFragment = new PaketFragment();
                        break;
                    case R.id.modul :
                        selectedFragment = new ModulFragment();
                        break;
                    case R.id.bantuan :
                        selectedFragment = new BantuanFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,
                        selectedFragment).commit();

                return true;
            }
        });
    }

    private void goToMenuUtama(){

        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container_fragment, homeFragment, "home_fragment").commit();
    }
}
