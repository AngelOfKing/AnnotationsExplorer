package com.bert.annotationsexplorer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: bertking
 * @ProjectName: AnnotationsExplorer
 * @CreateAt: 2020/8/14 8:49 PM
 * @UpdateAt: 2020/8/14 8:49 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description:
 */
public class ButterKnifeActivity extends AppCompatActivity {
    @BindView(R.id.btn_future)
    public Button btnFuture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.btn_future)
    public void welcomeToFuture(){
        Toast.makeText(this,"欢迎来到未来",Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
