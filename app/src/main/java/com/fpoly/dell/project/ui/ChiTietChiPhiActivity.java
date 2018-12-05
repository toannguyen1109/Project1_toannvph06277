package com.fpoly.dell.project.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.fpoly.dell.project.DatePickerFragment;
import com.fpoly.dell.project.dao.ChiPhiDao;
import com.fpoly.dell.project.dao.ChungLoaiDao;
import com.fpoly.dell.project.model.ChiPhi;
import com.fpoly.dell.project1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ChiTietChiPhiActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText edtMachiphi;
    private EditText edtTenthucan;
    private EditText edNgayMua;
    private EditText edtSoLuongChiPhi;
    private EditText edtGiatien;
    private Button btnHuy;
    private Button btnAdd;

    private String machiphi;
    private String tenthucan;
    private String ngaynhap;
    private String soluong;
    private String giatien;
    private ChiPhiDao chiPhiDao;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_chi_phi);

        setTitle("Chi Tiết Chi Phí");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView();

        chiPhiDao = new ChiPhiDao(ChiTietChiPhiActivity.this);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        machiphi = b.getString("MACHIPHI");
        tenthucan = b.getString("TENTHUCAN");
        ngaynhap = b.getString("NGAYNHAP");
        soluong = b.getString("SOLUONG");
        giatien = b.getString("GIATIEN");


        edtMachiphi.setText(machiphi);
        edtTenthucan.setText(tenthucan);
        edNgayMua.setText(ngaynhap);
        edtSoLuongChiPhi.setText(soluong);
        edtGiatien.setText(giatien);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View view) {
                if (validation() > 0) {
                    Date date = null;
                    try {
                      date = new SimpleDateFormat("dd-MM-yyyy").parse(edNgayMua.getText().toString());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (chiPhiDao.updateChiphi(edtMachiphi.getText().toString(), edtTenthucan.getText().toString(),
                            date, edtSoLuongChiPhi.getText().toString(), edtGiatien.getText().toString()) > 0) {
                        Toast.makeText(getApplicationContext(), "Lưu Thành Công", Toast.LENGTH_SHORT).show();
                        Intent a = new Intent(ChiTietChiPhiActivity.this, ChiPhiActivity.class);
                        startActivity(a);
                    }
                }

            }
        });
    }
    public static String convertDate(String dateTime) {
        Date date = new Date(dateTime);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }
    public void datePicker(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "date");
    }

    private void setDate(final Calendar calendar) {
        edNgayMua.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);

    }

    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);

        }
    }

    private int validation() {
        if
                (edtMachiphi.getText().toString().isEmpty() || edtTenthucan.getText().toString().isEmpty()
                || edNgayMua.getText().toString().isEmpty() || edtSoLuongChiPhi.getText().toString().isEmpty()
                || edtGiatien.getText().toString().isEmpty()
                ) {
            return -1;
        }
        return 1;
    }

    private void initView() {
        edtMachiphi = (EditText) findViewById(R.id.edtMachiphi);
        edtTenthucan = (EditText) findViewById(R.id.edtTenthucan);
        edNgayMua = (EditText) findViewById(R.id.edNgayMua);
        edtSoLuongChiPhi = (EditText) findViewById(R.id.edtSoLuong_ChiPhi);
        edtGiatien = (EditText) findViewById(R.id.edtGiatien);
        btnHuy = (Button) findViewById(R.id.btnHuy);
        btnAdd = (Button) findViewById(R.id.btnAdd);
    }


}
