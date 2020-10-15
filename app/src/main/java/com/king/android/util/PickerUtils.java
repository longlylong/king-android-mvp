package com.king.android.util;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.king.android.R;
import com.simga.library.activity.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.LinkagePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.util.DateUtils;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Author：SimGa
 * CoryRight：2018/11/16
 * PackageName：com.ewhale.imissyou.userside.utils
 */
public class PickerUtils {

    private static final PickerUtils ourInstance = new PickerUtils();
    private onCallBackAddress onCallBackAddress;
    private onCallBackOptions onCallBackOptions;
    private onCallBackDate onCallBackDate;
    private onCallBackTime onCallBackTime;

    public static PickerUtils getInstance() {
        return ourInstance;
    }

    public static final float getHeightInPx(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    public void pickerOption(BaseActivity mContext, String title, String[] items, final onCallBackOptions onCallBackOptions) {
        try {
            OptionPicker peiSongPicker = new OptionPicker(mContext, items);
            peiSongPicker.setDividerRatio(WheelView.DividerConfig.FILL);
            peiSongPicker.setTextSize(17);
            peiSongPicker.setTitleTextSize(17);
            peiSongPicker.setCancelTextSize(17);
            peiSongPicker.setSubmitTextSize(17);
            peiSongPicker.setCycleDisable(true);
            peiSongPicker.setTitleText(title);
            peiSongPicker.setTopHeight(50);
            peiSongPicker.setHeight((int) (getHeightInPx(mContext) / 2.5));
            peiSongPicker.setDividerColor(mContext.getResources().getColor(R.color.main_color));
            peiSongPicker.setSubmitTextColor(mContext.getResources().getColor(R.color.main_color));
            peiSongPicker.setCancelTextColor(mContext.getResources().getColor(R.color.main_color));
            peiSongPicker.setTextColor(mContext.getResources().getColor(R.color.main_color),
                    mContext.getResources().getColor(R.color.text_999999));
            peiSongPicker.setTitleTextColor(mContext.getResources().getColor(R.color.text_999999));
            peiSongPicker.setTopLineColor(mContext.getResources().getColor(R.color.diver_dcdcdc));
            peiSongPicker.setAnimationStyle(com.simga.library.R.style.bottom_int_out_dialog_style);
            peiSongPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    if (onCallBackOptions != null) {
                        onCallBackOptions.onOptionPicked(index, item);
                    }
                }
            });
            peiSongPicker.show();
        } catch (IllegalArgumentException e) {
            mContext.showToast("暂无数据");
            e.printStackTrace();
        }
    }

    public void pickerYearMouthDay(BaseActivity mContext, String title, final onCallBackDate onCallBackDate) {
        pickerYearMouthDay(mContext, title, 0, 0, 0, 0, 0, 0, onCallBackDate);
    }

    public void pickerYearMouthDay(BaseActivity mContext, String title, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, final onCallBackDate onCallBackDate) {
        Calendar calendar = Calendar.getInstance();
        if (startYear == 0) {
            startYear = 1900;
        }
        if (startMonth == 0) {
            startMonth = 1;
        }
        if (startDay == 0) {
            startDay = 1;
        }
        if (endYear == 0) {
            endYear = calendar.get(Calendar.YEAR);
            LogUtils.e("endYear" + endYear);
        }
        if (endMonth == 0) {
            endMonth = calendar.get(Calendar.MONTH) + 1;
            LogUtils.e("endMonth" + endMonth);
        }
        if (endDay == 0) {
            endDay = calendar.get(Calendar.DAY_OF_MONTH);
            LogUtils.e("endDay" + endDay);
        }
        DatePicker datePicker = new DatePicker(mContext);
        datePicker.setDividerRatio(WheelView.DividerConfig.FILL);
        datePicker.setTextSize(17);
        datePicker.setTitleTextSize(17);
        datePicker.setCancelTextSize(17);
        datePicker.setSubmitTextSize(17);
        datePicker.setCycleDisable(true);
        datePicker.setTitleText(title);
        datePicker.setTopHeight(50);
        datePicker.setUseWeight(false);
        datePicker.setHeight((int) (getHeightInPx(mContext) / 2.5));
        datePicker.setDividerColor(mContext.getResources().getColor(R.color.main_color));
        datePicker.setSubmitTextColor(mContext.getResources().getColor(R.color.main_color));
        datePicker.setCancelTextColor(mContext.getResources().getColor(R.color.main_color));
        datePicker.setTextColor(mContext.getResources().getColor(R.color.main_color),
                mContext.getResources().getColor(R.color.text_999999));
        datePicker.setLabelTextColor(mContext.getResources().getColor(R.color.main_color));
        datePicker.setTitleTextColor(mContext.getResources().getColor(R.color.text_999999));
        datePicker.setTopLineColor(mContext.getResources().getColor(R.color.diver_dcdcdc));
        datePicker.setAnimationStyle(com.simga.library.R.style.bottom_int_out_dialog_style);
        datePicker.setRangeStart(startYear, startMonth, startDay);
        datePicker.setSelectedItem(endYear, endMonth, endDay);
        datePicker.setRangeEnd(endYear, endMonth, endDay);
        datePicker.setResetWhileWheel(false);
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                if (onCallBackDate != null) {
                    onCallBackDate.onOptionPicked(year, month, day);
                }
            }
        });
        datePicker.show();
    }

    public void pickerYearMouth(BaseActivity mContext, String title, final onCallBackDate onCallBackDate) {
        Calendar calendar = Calendar.getInstance();
        DatePicker datePicker = new DatePicker(mContext, DateTimePicker.YEAR_MONTH);
        datePicker.setDividerRatio(WheelView.DividerConfig.FILL);
        datePicker.setTextSize(17);
        datePicker.setTitleTextSize(17);
        datePicker.setCancelTextSize(17);
        datePicker.setSubmitTextSize(17);
        datePicker.setCycleDisable(true);
        datePicker.setTitleText(title);
        datePicker.setTopHeight(50);
        datePicker.setUseWeight(true);
        datePicker.setUseWeight(false);
        datePicker.setHeight((int) (getHeightInPx(mContext) / 2.5));
        datePicker.setDividerColor(mContext.getResources().getColor(R.color.main_color));
        datePicker.setSubmitTextColor(mContext.getResources().getColor(R.color.main_color));
        datePicker.setCancelTextColor(mContext.getResources().getColor(R.color.main_color));
        datePicker.setTextColor(mContext.getResources().getColor(R.color.main_color),
                mContext.getResources().getColor(R.color.text_999999));
        datePicker.setLabelTextColor(mContext.getResources().getColor(R.color.main_color));
        datePicker.setTitleTextColor(mContext.getResources().getColor(R.color.text_999999));
        datePicker.setTopLineColor(mContext.getResources().getColor(R.color.diver_dcdcdc));
        datePicker.setAnimationStyle(com.simga.library.R.style.bottom_int_out_dialog_style);
        datePicker.setRangeStart(1970, 12);
        datePicker.setSelectedItem(1990, 1);
        datePicker.setRangeEnd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        datePicker.setResetWhileWheel(false);
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                if (onCallBackDate != null) {
                    onCallBackDate.onOptionPicked(year, month, "");
                }
            }
        });
        datePicker.show();
    }

    public void pickerTime(BaseActivity mContext, String title, final onCallBackTime onCallBackTime) {
        Calendar calendar = Calendar.getInstance();
        LinkagePicker.DataProvider provider = new LinkagePicker.DataProvider() {
            @Override
            public boolean isOnlyTwo() {
                return false;
            }

            @Override
            public List<String> provideFirstData() {
                ArrayList<String> firstList = new ArrayList<>();
                firstList.add("现在");
                firstList.add("今天");
                firstList.add("明天");
                firstList.add("后天");
                return firstList;
            }

            @Override
            public List<String> provideSecondData(int firstIndex) {
                ArrayList<String> secondList = new ArrayList<>();
                if (firstIndex == 0) {
                    secondList.add("");
                } else if (firstIndex == 1) {
                    int hour = (calendar.get(Calendar.MINUTE) + 15) >= 60 ? calendar.get(Calendar.HOUR_OF_DAY) + 1 : calendar.get(Calendar.HOUR_OF_DAY);
                    if (hour > 23) {
                        hour = 23;
                    }
                    for (int i = hour; i <= 23; i++) {
                        String str = DateUtils.fillZero(i);
                        secondList.add(str);
                    }
                } else if (firstIndex == 2 || firstIndex == 3) {
                    for (int i = 0; i <= 23; i++) {
                        String str = DateUtils.fillZero(i);
                        secondList.add(str);
                    }
                }
                return secondList;
            }

            @Override
            public List<String> provideThirdData(int firstIndex, int secondIndex) {
                ArrayList<String> thirdlist = new ArrayList<>();
                if (firstIndex == 0) {
                    thirdlist.add("");
                }
                if (firstIndex == 1) {
                    int minute;
                    if (secondIndex == 0) {
                        minute = (calendar.get(Calendar.MINUTE) + 15) >= 60 ? (15 - (60 - calendar.get(Calendar.MINUTE))) : (calendar.get(Calendar.MINUTE) + 15);
                    } else {
                        minute = 0;
                    }
                    for (int i = minute; i <= 59; i++) {
                        String str = DateUtils.fillZero(i);
                        thirdlist.add(str);
                    }
                } else if (firstIndex == 2 || firstIndex == 3) {
                    for (int i = 0; i <= 59; i++) {
                        String str = DateUtils.fillZero(i);
                        thirdlist.add(str);
                    }
                }
                return thirdlist;
            }
        };
        LinkagePicker picker = new LinkagePicker(mContext, provider);
        picker.setCycleDisable(true);
        picker.setLabel("", "", "");
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setTextSize(17);
        picker.setTitleTextSize(17);
        picker.setCancelTextSize(17);
        picker.setSubmitTextSize(17);
        picker.setCycleDisable(true);
        picker.setTitleText(title);
        picker.setTopHeight(50);
        picker.setUseWeight(true);
        picker.setHeight((int) (getHeightInPx(mContext) / 2.5));
        picker.setDividerColor(mContext.getResources().getColor(R.color.main_color));
        picker.setSubmitTextColor(mContext.getResources().getColor(R.color.main_color));
        picker.setCancelTextColor(mContext.getResources().getColor(R.color.main_color));
        picker.setTextColor(mContext.getResources().getColor(R.color.main_color),
                mContext.getResources().getColor(R.color.text_999999));
        picker.setLabelTextColor(mContext.getResources().getColor(R.color.main_color));
        picker.setTitleTextColor(mContext.getResources().getColor(R.color.text_999999));
        picker.setTopLineColor(mContext.getResources().getColor(R.color.diver_dcdcdc));
        picker.setAnimationStyle(com.simga.library.R.style.bottom_int_out_dialog_style);
        picker.setOnStringPickListener(new LinkagePicker.OnStringPickListener() {
            @Override
            public void onPicked(String first, String second, String third) {
                if (onCallBackTime != null)
                    onCallBackTime.onOptionPicked(first, second, third);
            }
        });
        picker.show();
    }

    public void pickerAddress(BaseActivity mContext, final onCallBackAddress callBackAddress) {
        ArrayList<Province> data = new ArrayList<>();
        try {
            String json = ConvertUtils.toString(mContext.getAssets().open("cityChoose.json"));
            data.addAll(JSON.parseArray(json, Province.class));
            AddressPicker addressPicker = new AddressPicker(mContext, data);
            addressPicker.setHideProvince(false);
            addressPicker.setHideCounty(false);
            addressPicker.setTitleText("所在地区");
            addressPicker.setTextSize(17);
            addressPicker.setTitleTextSize(17);
            addressPicker.setCancelTextSize(17);
            addressPicker.setSubmitTextSize(17);
            addressPicker.setTopHeight(50);
            addressPicker.setHeight((int) (getHeightInPx(mContext) / 2.5));
            addressPicker.setDividerColor(mContext.getResources().getColor(R.color.main_color));
            addressPicker.setSubmitTextColor(mContext.getResources().getColor(R.color.main_color));
            addressPicker.setCancelTextColor(mContext.getResources().getColor(R.color.main_color));
            addressPicker.setCycleDisable(true);
            addressPicker.setTextColor(mContext.getResources().getColor(R.color.main_color),
                    mContext.getResources().getColor(R.color.text_999999));
            addressPicker.setTitleTextColor(mContext.getResources().getColor(R.color.text_999999));
            addressPicker.setTopLineColor(mContext.getResources().getColor(R.color.diver_dcdcdc));
            addressPicker.setAnimationStyle(com.simga.library.R.style.bottom_int_out_dialog_style);
            addressPicker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    if (callBackAddress != null) {
                        callBackAddress.onAddressPicked(province, city, county);
                    }
                }
            });
            addressPicker.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOnCallBackTime(PickerUtils.onCallBackTime onCallBackTime) {
        this.onCallBackTime = onCallBackTime;
    }

    public void setOnCallBackAddress(PickerUtils.onCallBackAddress onCallBackAddress) {
        this.onCallBackAddress = onCallBackAddress;
    }

    public void setOnCallBackOptions(onCallBackOptions onCallBackOptions) {
        this.onCallBackOptions = onCallBackOptions;
    }

    public void setOnCallBackDate(onCallBackDate onCallBackDate) {
        this.onCallBackDate = onCallBackDate;
    }

    public interface onCallBackAddress {
        void onAddressPicked(Province province, City city, County county);
    }

    public interface onCallBackOptions {
        void onOptionPicked(int index, String item);
    }

    public interface onCallBackDate {
        void onOptionPicked(String year, String month, String day);
    }

    public interface onCallBackTime {
        void onOptionPicked(String first, String second, String third);
    }
}
