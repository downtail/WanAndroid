package com.downtail.wanandroid.widget.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.entity.response.WebsiteResponse;

import butterknife.BindView;
import butterknife.OnClick;

public class EditDialog extends BaseDialog {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etLink)
    EditText etLink;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;

    private OnEditListener onEditListener;
    private int code;
    private WebsiteResponse website;

    public static EditDialog getInstance() {
        Bundle args = new Bundle();
        EditDialog dialog = new EditDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_eidt;
    }

    @Override
    protected void initEvents() {
        if (code == 0) {

        } else if (code == 1) {
            etName.setText(website.getName());
            etLink.setText(website.getLink());
        }
    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected int getAvailableHeight() {
        return -2;
    }

    @OnClick({R.id.tvConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvConfirm:
                dealWithInput();
                break;
        }
    }

    private void dealWithInput() {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            if (onEditListener != null) {
                onEditListener.onHint(R.string.collectNameHint);
            }
            return;
        }
        String link = etLink.getText().toString().trim();
        if (TextUtils.isEmpty(link)) {
            if (onEditListener != null) {
                onEditListener.onHint(R.string.collectLinkHint);
            }
            return;
        }
        if (code == 0) {
            if (onEditListener != null) {
                onEditListener.onEdit(name, link);
            }
        } else if (code == 1) {
            if (onEditListener != null) {
                onEditListener.onComplete(website.getId(), name, link);
            }
        }
        dismiss();
    }

    public EditDialog setCode(int code) {
        this.code = code;
        return this;
    }

    public EditDialog setWebsite(WebsiteResponse website) {
        this.website = website;
        return this;
    }

    public EditDialog setOnEditListener(OnEditListener onEditListener) {
        this.onEditListener = onEditListener;
        return this;
    }

    public interface OnEditListener {
        void onHint(int resId);

        void onEdit(String name, String link);

        void onComplete(int id, String name, String link);
    }
}
