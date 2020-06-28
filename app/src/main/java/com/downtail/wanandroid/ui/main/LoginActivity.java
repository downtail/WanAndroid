package com.downtail.wanandroid.ui.main;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.base.activity.BaseActivity;
import com.downtail.wanandroid.contract.main.LoginContract;
import com.downtail.wanandroid.presenter.main.LoginPresenter;
import com.downtail.wanandroid.utils.ImageLoader;
import com.downtail.wanandroid.widget.plus.StatusBarPlus;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.ivLogin)
    ImageView ivLogin;
    @BindView(R.id.layoutAction)
    View layoutAction;
    @BindView(R.id.layoutShow)
    View layoutShow;
    @BindView(R.id.tvShow)
    TextView tvShow;
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.layoutRe)
    View layoutRe;
    @BindView(R.id.etRe)
    EditText etRe;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEvents() {
        ImageLoader.loadBlurImage(mActivity, ivLogin, R.drawable.login);

        layoutShow.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        layoutShow.getWindowVisibleDisplayFrame(r);
                        int screenHeight = layoutShow.getRootView().getHeight();
                        int heightDifference = screenHeight - (r.bottom);
                        if (heightDifference > 200) {
                            //软键盘显示
//                            layoutShow.setTranslationY(-heightDifference);

                        } else {
                            layoutShow.setTranslationY(0);
                        }
                    }
                });
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void onReload() {

    }

    @Override
    protected void initStatusBar() {
        StatusBarPlus.setTransparent(mActivity);
        StatusBarPlus.setStatusBarMode(this, false);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
    }

    @OnClick({R.id.tvLogin, R.id.tvRegister, R.id.layoutDrawer, R.id.tvConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLogin:
                enterWithAnim(0);
                break;
            case R.id.tvRegister:
                enterWithAnim(1);
                break;
            case R.id.layoutDrawer:
                exitWithAnim();
                break;
            case R.id.tvConfirm:
                dealWithInput();
                break;
            default:
                break;
        }
    }

    private void dealWithInput() {
        String account = etAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            toast(getResources().getString(R.string.userAccountToast));
            return;
        }
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            toast(getResources().getString(R.string.userPasswordToast));
            return;
        }
        int code = 0;
        Object tag = tvConfirm.getTag();
        if (tag instanceof Integer) {
            code = (Integer) tag;
        }
        if (code == 0) {
            mPresenter.login(account, password);
        } else if (code == 1) {
            String rePassword = etRe.getText().toString().trim();
            if (TextUtils.isEmpty(rePassword)) {
                toast(getResources().getString(R.string.userPasswordToast));
                return;
            }
            if (!password.equals(rePassword)) {
                toast(getResources().getString(R.string.userPasswordError));
                return;
            }
            mPresenter.register(account, password, rePassword);
        }
    }

    private void enterWithAnim(int code) {
        if (code == 0) {
            tvShow.setText(R.string.userLogin);
            layoutRe.setVisibility(View.GONE);
            tvConfirm.setTag(0);
        } else if (code == 1) {
            tvShow.setText(R.string.userRegister);
            layoutRe.setVisibility(View.VISIBLE);
            tvConfirm.setTag(1);
        }
        int visibility = layoutShow.getVisibility();
        if (visibility == View.GONE) {
            layoutAction.setVisibility(View.GONE);
            layoutShow.setVisibility(View.VISIBLE);
            Animation enterAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.enter_up_anim);
            enterAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            layoutShow.startAnimation(enterAnimation);
        }
    }

    private void exitWithAnim() {
        int visibility = layoutShow.getVisibility();
        if (visibility == View.VISIBLE) {
            Animation exitAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.exit_down_anim);
            exitAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    layoutAction.setVisibility(View.VISIBLE);
                    layoutShow.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            layoutShow.startAnimation(exitAnimation);
        }
    }

    @Override
    public void loginSuccess() {
        toast(getResources().getString(R.string.userLoginSuccess));
        finish();
    }
}
