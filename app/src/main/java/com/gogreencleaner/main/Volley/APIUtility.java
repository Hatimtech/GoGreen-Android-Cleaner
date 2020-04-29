package com.gogreencleaner.main.Volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gogreencleaner.main.Utils.CommonUtils;
import com.gogreencleaner.main.model.Assignment.REQUEST.AssignmentRequest;
import com.gogreencleaner.main.model.Assignment.RESPONSE.AssignmentResponseWrapper;
import com.gogreencleaner.main.model.AssignmentAttended.REQUEST.AssignmentAttendedRequest;
import com.gogreencleaner.main.model.AssignmentAttended.RESPONSE.AssignmentAttendedResponseWrapper;
import com.gogreencleaner.main.model.ChangePassword.REQUEST.ChangePasswordRequest;
import com.gogreencleaner.main.model.ChangePassword.RESPONSE.ChangePasswordResponseWrapper;
import com.gogreencleaner.main.model.CurrentAssignment.REQUEST.CurrentAssignmentRequest;
import com.gogreencleaner.main.model.CurrentAssignment.RESPONSE.CurrentAssignmentResponseWrapper;
import com.gogreencleaner.main.model.DashBoard.REQUEST.DashBoardRequest;
import com.gogreencleaner.main.model.DashBoard.RESPONSE.DashBoardResponseWrapper;
import com.gogreencleaner.main.model.FgPassword.REQUEST.FgPasswordRequest;
import com.gogreencleaner.main.model.FgPassword.RESPONSE.FgPasswordResponseWrapper;
import com.gogreencleaner.main.model.Login.REQUEST.LoginRequest;
import com.gogreencleaner.main.model.Login.RESPONSE.LoginResponseWrapper;
import com.gogreencleaner.main.model.PastAssignment.REQUEST.PastAssignmentRequest;
import com.gogreencleaner.main.model.PastAssignment.RESPONSE.PastAssignmentResponseWrapper;
import com.gogreencleaner.main.model.PaymentAssignment.REQUEST.PaymentAssignmentRequest;
import com.gogreencleaner.main.model.PaymentAssignment.RESPONSE.PaymentAssignmentResponseWrapper;
import com.gogreencleaner.main.model.PaymentCollectionStatus.REQUEST.PaymentCollectionStatusRequest;
import com.gogreencleaner.main.model.PaymentCollectionStatus.RESPONSE.PaymentCollectionStatusResponseWrapper;
import com.gogreencleaner.main.model.UpdateAttendedStatus.REQUEST.UpdateAttendedStatusRequest;
import com.gogreencleaner.main.model.UpdateAttendedStatus.RESPONSE.UpdateAttendedStatusResponseWrapper;
import com.gogreencleaner.main.model.UpdateInfo.REQUEST.UpdateInfoRequest;
import com.gogreencleaner.main.model.UpdateInfo.RESPONSE.UpdateInfoResponseWrapper;
import com.gogreencleaner.main.model.UpdatePassword.REQUEST.UpdatePasswordRequest;
import com.gogreencleaner.main.model.UpdatePassword.RESPONSE.UpdatePasswordResponseWrapper;


public class APIUtility {
    public final String TAG = "VOLLEY ";


    public APIUtility(Context context) {

    }

//    13.126.37.2-18
//    13.126.99.1_4


    public void showDialog(Context context, boolean isDialog) {
        if (isDialog) {
            ProcessDialog.start(context);
        }
    }

    public void dismissDialog(boolean isDialog) {
        if (isDialog) {
            ProcessDialog.dismiss();
        }
    }

    public interface APIResponseListener<T> {
        void onReceiveResponse(T response);

        void onResponseFailed();

        void onStatusFalse(T response);
    }

    public void userLogin(final Context context, LoginRequest loginRequest, final boolean showDialog, final APIResponseListener<LoginResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<LoginResponseWrapper> request = new GenericRequest<LoginResponseWrapper>(Request.Method.POST, "http://13.126.37.218/gogreen/index.php/cleaner_api_v2/", LoginResponseWrapper.class,
                loginRequest, new Response.Listener<LoginResponseWrapper>() {
            @Override
            public void onResponse(LoginResponseWrapper response) {

                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }


    public void forgetPassword(final Context context, FgPasswordRequest fgPasswordRequest, final boolean showDialog, final APIResponseListener<FgPasswordResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest< FgPasswordResponseWrapper> request = new GenericRequest< FgPasswordResponseWrapper>(Request.Method.POST, "http://13.126.37.218/gogreen/index.php/cleaner_api_v2/", FgPasswordResponseWrapper.class,
               fgPasswordRequest, new Response.Listener< FgPasswordResponseWrapper>() {
            @Override
            public void onResponse( FgPasswordResponseWrapper response) {

                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);

    }


    public void updatePassword(final Context context, UpdatePasswordRequest updatePasswordRequest, final boolean showDialog, final APIResponseListener<UpdatePasswordResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest< UpdatePasswordResponseWrapper> request = new GenericRequest< UpdatePasswordResponseWrapper>(Request.Method.POST, "http://13.126.37.218/gogreen/index.php/cleaner_api_v2/", UpdatePasswordResponseWrapper.class,
                updatePasswordRequest, new Response.Listener< UpdatePasswordResponseWrapper>() {
            @Override
            public void onResponse( UpdatePasswordResponseWrapper response) {

                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }



    public void changePassword(final Context context, ChangePasswordRequest changePasswordRequest, final boolean showDialog, final APIResponseListener<ChangePasswordResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest< ChangePasswordResponseWrapper> request = new GenericRequest< ChangePasswordResponseWrapper>(Request.Method.POST, "http://13.126.37.218/gogreen/index.php/cleaner_api_v2/", ChangePasswordResponseWrapper.class,
                changePasswordRequest, new Response.Listener< ChangePasswordResponseWrapper>() {
            @Override
            public void onResponse( ChangePasswordResponseWrapper response) {

                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }


    public void  getDashboardDetail(final Context context, DashBoardRequest dashBoardRequest, final boolean showDialog, final APIResponseListener<DashBoardResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<DashBoardResponseWrapper> request = new GenericRequest<DashBoardResponseWrapper>(Request.Method.POST, "http://13.126.37.218/gogreen/index.php/orders_api_v2/", DashBoardResponseWrapper.class,
                dashBoardRequest, new Response.Listener< DashBoardResponseWrapper>() {
            @Override
            public void onResponse( DashBoardResponseWrapper response) {

                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }


    public void getCurrentAssignment(final Context context, CurrentAssignmentRequest currentAssignmentRequest, final boolean showDialog, final APIResponseListener<CurrentAssignmentResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<CurrentAssignmentResponseWrapper> request = new GenericRequest< CurrentAssignmentResponseWrapper>(Request.Method.POST, "http://13.126.37.218/gogreen/index.php/orders_api_v2/", CurrentAssignmentResponseWrapper.class,
                currentAssignmentRequest, new Response.Listener< CurrentAssignmentResponseWrapper>() {
            @Override
            public void onResponse( CurrentAssignmentResponseWrapper response) {

                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }


    public void getPastAssignment(final Context context, PastAssignmentRequest pastAssignmentRequest, final boolean showDialog, final APIResponseListener<PastAssignmentResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<PastAssignmentResponseWrapper> request = new GenericRequest< PastAssignmentResponseWrapper>(Request.Method.POST, "http://13.126.37.218/gogreen/index.php/orders_api_v2/", PastAssignmentResponseWrapper.class,
                pastAssignmentRequest, new Response.Listener<PastAssignmentResponseWrapper>() {
            @Override
            public void onResponse( PastAssignmentResponseWrapper response) {

                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }


    public void getPaymentAssignment(final Context context, PaymentAssignmentRequest paymentAssignmentRequest, final boolean showDialog, final APIResponseListener<PaymentAssignmentResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<PaymentAssignmentResponseWrapper> request = new GenericRequest< PaymentAssignmentResponseWrapper>(Request.Method.POST, "http://13.126.37.218/gogreen/index.php/orders_api_v2/", PaymentAssignmentResponseWrapper.class,
                paymentAssignmentRequest, new Response.Listener<PaymentAssignmentResponseWrapper>() {
            @Override
            public void onResponse( PaymentAssignmentResponseWrapper response) {

                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }


    public void sendAttendedStatus(final Context context, AssignmentAttendedRequest attendedRequest, final boolean showDialog, final APIResponseListener<AssignmentAttendedResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<AssignmentAttendedResponseWrapper> request = new GenericRequest<AssignmentAttendedResponseWrapper>(Request.Method.POST, "http://13.126.37.218/gogreen/index.php/orders_api_v2/", AssignmentAttendedResponseWrapper.class,
                attendedRequest, new Response.Listener<AssignmentAttendedResponseWrapper>() {
            @Override
            public void onResponse(AssignmentAttendedResponseWrapper response) {

                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    public void updateAttendentStatus(final Context context, UpdateAttendedStatusRequest attendedRequest, final boolean showDialog, final APIResponseListener<UpdateAttendedStatusResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<UpdateAttendedStatusResponseWrapper> request = new GenericRequest<UpdateAttendedStatusResponseWrapper>(Request.Method.POST, "http://13.126.37.218/gogreen/index.php/orders_api_v2/", UpdateAttendedStatusResponseWrapper.class,
                attendedRequest, new Response.Listener<UpdateAttendedStatusResponseWrapper>() {
            @Override
            public void onResponse(UpdateAttendedStatusResponseWrapper response) {

                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    public void sendPaymentStatus(final Context context, PaymentCollectionStatusRequest paymentCollectionStatusRequest, final boolean showDialog, final APIResponseListener<PaymentCollectionStatusResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<PaymentCollectionStatusResponseWrapper> request = new GenericRequest<PaymentCollectionStatusResponseWrapper>(Request.Method.POST, "http://13.126.37.218/gogreen/index.php/orders_api_v2/", PaymentCollectionStatusResponseWrapper.class,
                paymentCollectionStatusRequest, new Response.Listener<PaymentCollectionStatusResponseWrapper>() {
            @Override
            public void onResponse(PaymentCollectionStatusResponseWrapper response) {

                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    public void updateUserInfo(final Context context, UpdateInfoRequest loginRequest, final boolean showDialog, final APIResponseListener<UpdateInfoResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<UpdateInfoResponseWrapper> request = new GenericRequest<UpdateInfoResponseWrapper>(Request.Method.POST, "http://13.126.37.218/gogreen/index.php/cleaner_api_v2/", UpdateInfoResponseWrapper.class,
                loginRequest, new Response.Listener<UpdateInfoResponseWrapper>() {
            @Override
            public void onResponse(UpdateInfoResponseWrapper response) {

                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}




