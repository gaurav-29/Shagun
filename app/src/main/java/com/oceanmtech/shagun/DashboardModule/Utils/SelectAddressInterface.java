package com.oceanmtech.shagun.DashboardModule.Utils;

import com.oceanmtech.shagun.DashboardModule.Models.ManageAddressModel;

public interface SelectAddressInterface {

    void selectedAddress(String address, String city, String postal_code, String country, String phone, ManageAddressModel.Data data);
}
