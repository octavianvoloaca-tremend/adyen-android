/*
 * Copyright (c) 2020 Adyen N.V.
 *
 * This file is open source and available under the MIT license. See the LICENSE file for more info.
 *
 * Created by josephj on 4/12/2020.
 */

package com.adyen.checkout.blik;

import androidx.annotation.NonNull;

import com.adyen.checkout.base.PaymentComponentProvider;
import com.adyen.checkout.base.PaymentComponentState;
import com.adyen.checkout.base.component.BasePaymentComponent;
import com.adyen.checkout.base.component.GenericPaymentComponentProvider;
import com.adyen.checkout.base.component.GenericPaymentMethodDelegate;
import com.adyen.checkout.base.model.payments.request.BlikPaymentMethod;
import com.adyen.checkout.base.model.payments.request.PaymentComponentData;
import com.adyen.checkout.base.util.PaymentMethodTypes;
import com.adyen.checkout.core.log.LogUtil;
import com.adyen.checkout.core.log.Logger;

public class BlikComponent extends BasePaymentComponent<BlikConfiguration, BlikInputData, BlikOutputData, PaymentComponentState<BlikPaymentMethod>> {
    private static final String TAG = LogUtil.getTag();

    public static final PaymentComponentProvider<BlikComponent, BlikConfiguration> PROVIDER =
            new GenericPaymentComponentProvider<>(BlikComponent.class);

    private static final String[] PAYMENT_METHOD_TYPES = {PaymentMethodTypes.BLIK};

    public BlikComponent(@NonNull GenericPaymentMethodDelegate paymentMethodDelegate, @NonNull BlikConfiguration configuration) {
        super(paymentMethodDelegate, configuration);
    }

    @NonNull
    @Override
    protected BlikOutputData onInputDataChanged(@NonNull BlikInputData inputData) {
        Logger.v(TAG, "onInputDataChanged");
        return new BlikOutputData(inputData.getBlikCode());
    }

    @NonNull
    @Override
    protected PaymentComponentState<BlikPaymentMethod> createComponentState() {

        final BlikOutputData blikOutputData = getOutputData();

        final PaymentComponentData<BlikPaymentMethod> paymentComponentData = new PaymentComponentData<>();
        final BlikPaymentMethod paymentMethod = new BlikPaymentMethod();
        paymentMethod.setType(BlikPaymentMethod.PAYMENT_METHOD_TYPE);

        if (blikOutputData != null) {
            paymentMethod.setBlikCode(blikOutputData.getBlikCodeField().getValue());
        }

        paymentComponentData.setPaymentMethod(paymentMethod);

        return new PaymentComponentState<>(paymentComponentData, blikOutputData != null && blikOutputData.isValid());
    }

    @NonNull
    @Override
    public String[] getSupportedPaymentMethodTypes() {
        return PAYMENT_METHOD_TYPES;
    }
}
