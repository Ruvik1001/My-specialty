package com.tasks.university_info;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tasks.domain.university.data.ContactInfo;
import com.tasks.domain.university.data.UniversityListItem;
import com.tasks.domain.university.data.WorkingInfo;

import java.util.ArrayList;
import java.util.List;

public class UniversityInfoFragment extends Fragment {

    private UniversityInfoViewModel mViewModel;

    private ImageView organizationPhoto;
    private TextView organizationName;
    private TextView address;
    private LinearLayout contactInfoContainer;

    LayoutInflater inflater;
    ViewGroup parent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_university_info, container, false);
        this.inflater = inflater;
        this.parent = container;

        organizationPhoto = view.findViewById(R.id.organizationPhoto);
        organizationName = view.findViewById(R.id.organizationName);
        address = view.findViewById(R.id.address);
        contactInfoContainer = view.findViewById(R.id.contactInfoContainer);

        mViewModel = new ViewModelProvider(this).get(UniversityInfoViewModel.class);
        loadData();
        setDataInFiled();

        return view;
    }

    private void loadData() {
        Bundle bundle = getArguments();
        if (!mViewModel.saved()) {
            mViewModel.setItem(Pair.create(
                    (UniversityListItem) bundle.getSerializable(
                            getString(com.tasks.core.R.string.KEY_BUNDLE_UNIVERSITY_TO_UNIVERSITY_INFO_UNIVERSITY_LIST_ITEM)
                    ),
                    bundle.getString(
                            getString(com.tasks.core.R.string.KEY_BUNDLE_UNIVERSITY_TO_UNIVERSITY_INFO_IMAGE_URL)
                    ))
            );
        }
    }

    private void setDataInFiled() {
        String id = "pic_id_" + mViewModel.getUniversityInfo().getGlobalId();
        int imageResource = getResources().getIdentifier(id, "drawable", getContext().getPackageName());
        if (imageResource != 0) {
            Picasso.get()
                    .load(imageResource)
                    .placeholder(com.tasks.core.R.drawable.vuz_default)
                    .into(organizationPhoto);
        } else if (mViewModel.getUniversityImage() != null && !mViewModel.getUniversityImage().isEmpty()) {
            Picasso.get()
                    .load(mViewModel.getUniversityImage())
                    .placeholder(com.tasks.core.R.drawable.vuz_default)
                    .into(organizationPhoto);
        } else {
            organizationPhoto.setImageResource(com.tasks.core.R.drawable.vuz_default);
        }

        organizationName.setText(mViewModel.getUniversityInfo().getCells().getShortName());
        address.setText(mViewModel.getUniversityInfo().getCells().getContactInfo().get(0).getLocation());
        address.setOnClickListener(v -> {
            String addressText = address.getText().toString();
            Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(addressText));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
            startActivity(mapIntent);
        });

        List<ContactInfo> contactInfo = mViewModel.getUniversityInfo().getCells().getContactInfo();
        for (int ii = 0; ii < contactInfo.size(); ++ii) {
            contactInfoContainer.addView(getContact(contactInfo.get(ii)));
        }
    }

    private View getContact(ContactInfo contact) {
        View view = inflater.inflate(R.layout.contact_item, parent, false);

        TextView contactName = view.findViewById(R.id.contactName);
        TextView position = view.findViewById(R.id.position);
        TextView phone = view.findViewById(R.id.phone);
        TextView email = view.findViewById(R.id.email);
        LinearLayout workingHoursContainer = view.findViewById(R.id.workingHoursContainer);

        contactName.setText(contact.getFio() + " - " + contact.getExtraInfo());
        position.setText(contact.getPosition());
        try {
            phone.setText(contact.getPhone().get(0).getPhone());
            phone.setOnClickListener(v -> {
                String phoneNumber = contact.getPhone().get(0).getPhone();
                if (!phoneNumber.isEmpty()) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse(getString(com.tasks.core.R.string.intentTelUriPrefics) + phoneNumber));
                    startActivity(dialIntent);
                } else {
                    Toast.makeText(requireContext(), getString(R.string.phone_number_is_empty), Toast.LENGTH_SHORT).show();
                }
            });
            email.setText(contact.getEmail().get(0).getEmail());
            email.setOnClickListener(v -> {
                String emailAddress = contact.getEmail().get(0).getEmail();
                if (!emailAddress.isEmpty()) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse(getString(com.tasks.core.R.string.intentEmailUriPrefics) + emailAddress));
                    startActivity(emailIntent);
                } else {
                    Toast.makeText(requireContext(), getString(R.string.email_address_is_empty), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ignored) { }

        for (int ii = 0; ii < contact.getWorkingInfo().size(); ++ii) {
            workingHoursContainer.addView(getWorkingHour(contact.getWorkingInfo().get(ii)));
        }

        return view;
    }

    @SuppressLint("SetTextI18n")
    private View getWorkingHour(WorkingInfo workingInfo) {
        View view = inflater.inflate(R.layout.work_time_item, parent, false);

        TextView workDay = view.findViewById(R.id.workDay);
        TextView workTime = view.findViewById(R.id.workTime);
        TextView lunchTime = view.findViewById(R.id.lunchTime);

        workDay.setText(workingInfo.getDayOfWeek());
        workTime.setText(workingInfo.getHours());
        lunchTime.setText(String.format(getString(R.string.lunch_f), workingInfo.getLunchBreak()));

        return view;
    }

}
