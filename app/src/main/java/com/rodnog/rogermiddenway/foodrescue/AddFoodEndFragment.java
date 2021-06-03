package com.rodnog.rogermiddenway.foodrescue;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.File;
import java.util.Arrays;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFoodEndFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFoodEndFragment extends Fragment {

    OnAddEndListener mCallback;

    TextView cStartDateTextView;
    TextView cStartTimeTextView;
    TextView cEndDateTextView;
    TextView cEndTimeTextView;

    String cFoodImagePath;
    String cFoodTitle;

    LatLng cFoodLocation;
    long cFoodStartTime;
    long cFoodEndTime;

    //TODO ADD START AND END TIME PICKERS

    Button cPostButton;
    Button cBackButton;

    Calendar date;

    public AddFoodEndFragment() {
        // Required empty public constructor
    }

    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFoodEndFragment.
     */
    // TODO: Rename and change types and number of parameters
    public interface OnAddEndListener {
//        public void onCloseC(LatLng location, long start, long end)
        public void onCloseC(LatLng location, long startTime, long endTime);
        public void onLocationSelected(LatLng location);
        public void onStartTimeSelected(long startTime);
        public void onEndTimeSelected(long endTime);
    }

    public static AddFoodEndFragment newInstance() {
        AddFoodEndFragment fragment = new AddFoodEndFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallback = (AddFoodEndFragment.OnAddEndListener) getActivity();
//        if (getArguments() != null) {
//            cFoodImagePath = getArguments().getString("PATH");
//            cFoodTitle = getArguments().getString("TITLE");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_food_end, container, false);
    }
    public void showDateTimePicker(boolean isStartTime) {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm");

                        String displayedTime = sdf.format(date.getTime());
                        if(isStartTime){
                            cFoodStartTime = date.getTimeInMillis();
                            cStartDateTextView.setText(displayedTime);
                            onStartTimeSelected(cFoodStartTime);
                        }
                        else{
                            cFoodEndTime = date.getTimeInMillis();
                            cEndDateTextView.setText(displayedTime);
                            onEndTimeSelected(cFoodEndTime);
                        }
                        Log.v("TIMEPICKER", "The choosen one " + date.getTime());
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
//        ImageView cFoodImageView = getActivity().findViewById(R.id.aFoodImageView);
//        TextView cFoodTitleTextView = getActivity().findViewById(R.id.cFoodTitleTextView);

        cStartDateTextView = getActivity().findViewById(R.id.cDateStartTextView);
//        cStartTimeTextView = getActivity().findViewById(R.id.cTimeEndTextView);
        cEndDateTextView = getActivity().findViewById(R.id.cDateEndTextView);
//        cEndTimeTextView = getActivity().findViewById(R.id.cTimeEndTextView);

        cStartDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(true);
            }
        });
        cEndDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(false);
            }
        });

//        File foodImage = new File(cFoodImagePath);
//        Bitmap foodBitmap = BitmapFactory.decodeFile(foodImage.getAbsolutePath());
//        cFoodImageView.setImageBitmap(foodBitmap);
//
//        cFoodTitleTextView.setText(cFoodTitle);

//        cBackButton = getActivity().findViewById(R.id.cBackButton);
//        cPostButton = getActivity().findViewById(R.id.cPostButton);
//
//        cBackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFragmentManager().popBackStackImmediate();
//            }
//        });
//
//        cPostButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(cFoodLocation != null && cFoodStartTime >0 && cFoodEndTime > 0) {
//                    onCloseC(cFoodLocation, cFoodStartTime, cFoodEndTime);
//                }
//                else {
//                    Toast.makeText(getActivity(), "Select pickup location and times", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        Places.initialize(getActivity(), getString(R.string.API_KEY));

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(getActivity());
        // Initialize the AutocompleteSupportFragment.
//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getActivity().getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager()
                        .findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                cFoodLocation = place.getLatLng();
                onLocationSelected(cFoodLocation);
                Log.i("PLACE", "Place: " + place.getName() + ", " + place.getId());
//                Toast.makeText(PlaceActivity.this, "Place: " + place.getName() + ", " + place.getId(), Toast.LENGTH_LONG).show();
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("PLACE", "An error occurred: " + status);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            onCloseC(cFoodLocation, cFoodStartTime, cFoodEndTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void onCloseC(LatLng location, long startTime, long endTime) {
        Log.d("ADDEND", "TImes are " + startTime + " " + endTime);
        if(mCallback != null)
        {
            mCallback.onCloseC(location, startTime, endTime);
        }
    }
    public void onLocationSelected(LatLng location) {
        if(mCallback != null)
        {
            mCallback.onLocationSelected(location);
        }
    }
    public void onStartTimeSelected(long startTime) {
        if(mCallback != null)
        {
            mCallback.onStartTimeSelected(startTime);
        }
    }
    public void onEndTimeSelected(long endTime) {
        if(mCallback != null)
        {
            mCallback.onEndTimeSelected(endTime);
        }
    }
}