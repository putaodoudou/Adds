//package com.adds;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnCancelListener;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.SwitchCompat;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.adds.authentication.DSPermissionsHelper;
//
//import org.apache.commons.lang.StringUtils;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.UnrecoverableKeyException;
//import java.security.cert.CertificateException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import javax.net.ssl.SSLPeerUnverifiedException;
//
//import de.consorsbank.CCBaseFragment;
//import de.consorsbank.R;
//import de.consorsbank.core.CCConfig;
//import de.consorsbank.core.CCConfigBase;
//import de.consorsbank.core.CCCoreConst;
//import de.consorsbank.core.eventbus.CCEventBus;
//import de.consorsbank.core.eventbus.CCEventBusWrapperImpl;
//import de.consorsbank.core.eventbus.events.error.impl.CCServiceErrorEvent;
//import de.consorsbank.core.eventbus.events.error.impl.CCSystemErrorEvent;
//import de.consorsbank.core.logging.CCLogger;
//import de.consorsbank.core.persistence.CCSharedPreferencUtils;
//import de.consorsbank.core.tracking.CCTrackingAdapter;
//import de.consorsbank.core.tracking.ICCTrackingNameConst;
//import de.consorsbank.core.tracking.types.action.CCTrackingActionDataForLogin;
//import de.consorsbank.core.tracking.types.action.ICCTrackingActionDataForTouchedOrPressed;
//import de.consorsbank.core.tracking.types.screen.ICCTrackingScreenData;
//import de.consorsbank.core.utilities.ApplicationContextHolder;
//import de.consorsbank.core.v2.domain.logic.CBConfigHelper;
//import de.consorsbank.core.v2.domain.model.data.CBConfigurationData;
//import de.consorsbank.core.v2.domain.model.handshake.CBDisplayButton;
//import de.consorsbank.core.v2.domain.model.handshake.CBDisplayButtonAction;
//import de.consorsbank.core.v2.domain.model.handshake.CBDisplayMessage;
//import de.consorsbank.dialog.model.CCDialogModel;
//import de.consorsbank.dialog.ui.CCDialogFragment;
//import de.consorsbank.dialog.ui.CCDialogListener;
//import de.consorsbank.dialog.ui.CCSimpleDialogFragment;
//import de.consorsbank.fingerprint.CBCryptographyHelper;
//import de.consorsbank.fingerprint.CBFingerPrintUIHelper;
//import de.consorsbank.fingerprint.CBRootDetectionHelper;
//import de.consorsbank.generic.ui.CCCommonMessageDialog;
//import de.consorsbank.module.CCAuthenticationListener;
//import de.consorsbank.module.CCAuthenticationManager;
//import de.consorsbank.module.CCAuthenticationModuleGlobal.AUTHENTICATIONMODULE;
//import de.consorsbank.module.accounts.CBAccountsFacade;
//import de.consorsbank.module.accounts.impl.CBAccountsFacadeImpl;
//import de.consorsbank.module.login.CCLoginCredentials;
//import de.consorsbank.module.login.CCLoginFacade;
//import de.consorsbank.module.login.CCLoginFacade.UICallBack;
//import de.consorsbank.module.login.model.CCLoginResponse;
//import de.consorsbank.module.login.v2.model.response.CBLoginResponse;
//import de.consorsbank.module.watchlist.v2.CBWatchlistMediator;
//import de.consorsbank.module.watchlist.v2.domain.impl.CBWatchlistMediatorImpl;
//import de.consorsbank.permissions.CBPermissionsHelper;
//import de.consorsbank.utils.CCKeyBoardStatus;
//import de.consorsbank.utils.CCKeyboardHandle;
//import de.consorsbank.utils.CCUiAlertUtility;
//import de.consorsbank.utils.UiUtility;
//import de.consorsbank.webview.ui.CCCommonWebViewFragment;
//import de.consorsbank.widgets.CCLoadingViewButton;
//import de.consorsbank.widgets.popup.CCCustomDialog;
//import de.consorsbank.widgets.popup.CCPopUpActivityContentFactory.CCPopUpContentTypeEnum;
//import de.consorsbank.widgets.popup.CCPopupActivity;
//
///**
// * General login fragment
// *
// * @updatedBy Vijeesh
// * @updatedBy cs93263
// */
//public class CCLoginFragment extends CCBaseFragment implements CCKeyboardHandle, ICCTrackingScreenData, CCDialogListener,
//		CCAuthenticationListener, CBFingerPrintUIHelper.ResponseCallback, DSPermissionsHelper.PermissionsCallback {
//
//	private static final String DIALOG_TAG = "dialogTag";
//	private final String LOGIN_ERROR_FAULT_CODE_0 = "2500";
//	private final String LOGIN_ERROR_FAULT_CODE_1 = "2501";
//	private final String LOGIN_ERROR_FAULT_CODE_2 = "2502";
//	private final String LOGIN_ERROR_FAULT_CODE_3 = "2503";
//	private final int MAX_USERNAME_LENGTH = 10;
//	private final int MIN_USERNAME_LENGTH = 7;
//	private final int MIN_PASSWORD_LENGTH = 5;
//	private EditText userNameText;
//	private EditText passwordText;
//	private CCLoadingViewButton loginButton;
//	private ImageView closeIcon;
//	private SwitchCompat rememberLogin;
//	private View rootView;
//	private View dividerView;
//	private TextView loginHeaderTextView;
//	private View tabletSubview;
//	private TextView mRemeberMeTxt;
//	private TextView mForgetPasswordTxt;
//	private boolean isParallexLogin;
//	private String userName;
//	private String password;
//	private Fragment ccsubContent;
//	private boolean isFPEnableConfirmPopupVisible = false;
//	private boolean isDenyPopupVisible = false;
//	private RelativeLayout loginLayout;
//	//	private SwitchCompat switchCompat;
//
//	private CBAccountsFacade mAccountFacade = CBAccountsFacadeImpl.getInstance();
//	private CBWatchlistMediator mWLMediator = CBWatchlistMediatorImpl.getInstance();
//	private CCEventBus mEventBus = CCEventBusWrapperImpl.getInstance();
//	private DSPermissionsHelper mPermissionHelper;
//	private boolean mFingerprintLoginSuccess = false;
//	private int mPendingPermissionDialogStatus = -1;
//	private String[] mPermissions;
//
//	/*
//	 * Its better to initialize non-static member variables in a constructor or
//	 * instance initializer
//	 */
//	public CCLoginFragment() {
//		userName = null;
//		password = null;
//
//	}
//
//	public static CCLoginFragment newInstance(Bundle bundle) {
//		CCLoginFragment ccLoginFragment = new CCLoginFragment();
//		ccLoginFragment.setArguments(bundle);
//		return ccLoginFragment;
//	}
//
//	public static boolean isServerTrusted() {
//		return CCConfig.isServerTrusted();
//	}
//
//	@Override
//	public View onCreateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		rootView = inflater.inflate(R.layout.cc_login_fragment_view, null);
//		this.mPermissionHelper = new DSPermissionsHelper(this, this);
//		if (getArguments() != null) {
//			isParallexLogin = getArguments().getBoolean(CCPXLoginFragment.IS_PARALLEX_LOGIN, false);
//		}
//		initUI();
//		loadRememberedCredentails();
//		showLoginStatus(true);
//
//		if (isParallexLogin) {
//			setFocusable(false);
//			hideLoginHeaderView();
//		} else {
//			CCKeyBoardStatus.showKeyBoard(getActivity());
//		}
//
//		return rootView;
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		if (CCConfig.isTablet() && !isParallexLogin && !CCConfig.isWMApp()) {
//			Fragment fragment = getChildFragmentManager().findFragmentById(R.id.login_fragment_inner_right_layout);
//			if (fragment == null) {
//				// Load CMS Fragment
//				FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//
//				Bundle bundle = new Bundle();
//				bundle.putString(ICCTrackingNameConst.BUSINESS_NAME_OF_SCREEN, getBusinessNameOfScreen());
//				bundle.putString(ICCTrackingNameConst.SECTION_NAME_OF_SCREEN, getSectionNameOfScreen());
//
//				ccsubContent = CCLoginCMSFragment.newInstance(bundle);
//				ft.add(R.id.login_fragment_inner_right_layout, ccsubContent);
//				ft.commit();
//			} else {
//				ccsubContent = fragment;
//			}
//		}
//	}
//
//	@Override
//	public void onStart() {
//		super.onStart();
//		/*
//		 * !!! ATTENTION !!! if we don't use this flag a part of the parallax
//		 * login fragment will be shown in the start page
//		 */
//		if (!isParallexLogin) {
//			setRequestFocus();
//			//Checks whether device have fingerprint and Loggin in for the first time.
//			if (CCConfigBase.sFingerprintCompatibleDevice && CCSharedPreferencUtils.getBooleanPref(CCCoreConst.SETTINGS.FINGERPRINT_LOGIN_ACTIVATED)
//					&& !(CCSharedPreferencUtils.getBooleanPref(AUTHENTICATIONMODULE.IS_LOGGING_IN_FOR_FIRST_TIME, true))) {
//				showFingerPrintDialog();
//			}
//		}
//	}
//
//	// Hide header in all devices and cms part in smartphone
//	private void hideLoginHeaderView() {
//		closeIcon.setVisibility(View.GONE);
//		loginHeaderTextView.setVisibility(View.GONE);
//		dividerView.setVisibility(View.GONE);
//		if (CCConfig.isTablet()) {
//			tabletSubview.setVisibility(View.GONE);
//		}
//	}
//
//	/*
//	 * Initialize UI elements
//	 */
//	private void initUI() {
//		userNameText = (EditText) rootView.findViewById(R.id.login_user_name);
//		passwordText = (EditText) rootView.findViewById(R.id.login_password);
//		loginButton = (CCLoadingViewButton) rootView.findViewById(R.id.login_button);
//		rememberLogin = (SwitchCompat) rootView.findViewById(R.id.login_fragment_remember_user_checkbox);
//		closeIcon = (ImageView) rootView.findViewById(R.id.login_fragment_close_button);
//		loginHeaderTextView = (TextView) rootView.findViewById(R.id.login_fragmert_header_textview);
//		mForgetPasswordTxt = (TextView) rootView.findViewById(R.id.login_fragment_forgot_password_textview);
//		mRemeberMeTxt = (TextView) rootView.findViewById(R.id.login_fragment_remember_me_textview);
//
//		if (isParallexLogin) {
//			mForgetPasswordTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.startpage_text_icon_color));
//			mForgetPasswordTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_key_white_12_5dp, 0, R.drawable.ic_arrow_right_white_16dp, 0);
//			UiUtility.colourDrawables(mForgetPasswordTxt.getCompoundDrawables(), ContextCompat.getColor(getContext(), R.color.startpage_text_icon_color));
//			mRemeberMeTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.startpage_text_icon_color));
//		}
//		if (!CCConfig.isWMApp()) {
//			mForgetPasswordTxt.setVisibility(View.VISIBLE);
//		}
//		dividerView = rootView.findViewById(R.id.login_fragment_header_divider);
//
//		if (!isParallexLogin) {
//			loginLayout = (RelativeLayout) rootView.findViewById(R.id.loginlayout);
//			int actionBarHeight = 0;
//			TypedValue tv = new TypedValue();
//			if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
//				actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
//			}
//
//			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, actionBarHeight);
//
//			loginLayout.setLayoutParams(params);
//		}
//
//		attachListeners();
//
//		if (CCConfig.isTablet()) {
//			tabletSubview = rootView.findViewById(R.id.login_fragment_inner_right_layout);
//		}
//	}
//
//	/*
//		 * Attach UI elements
//		 */
//	private void attachListeners() {
//		rootView.findViewById(R.id.login_fragment_forgot_password_textview).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), CCPopupActivity.class);
//				intent.putExtra(CCPopupActivity.CONTENT_TYPE, CCPopUpContentTypeEnum.INAPP_BROWSER);
//				Bundle arguments = new Bundle();
//				arguments.putString(CCCommonWebViewFragment.URL, CBConfigHelper.newInstance().getValue(CBConfigurationData.URL_KEY.FORGOT_PASSWORD_URL));
//				intent.putExtras(arguments);
//				startActivity(intent);
//				if (CCConfig.isTablet()) {
//					getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//				}
//
//				// //////////////////////////////////////////////////////////////////////
//				//
//				// cs7627: we need this listener to track action touched
//				//
//				CCTrackingAdapter.getCCTracking().trackActionForTouchedOrPressed(new ICCTrackingActionDataForTouchedOrPressed() {
//
//					@Override
//					public String getBusinessNameOfScreen() {
//						return CCLoginFragment.this.getBusinessNameOfScreen();
//					}
//
//					@Override
//					public String getBusinessNameOfAction() {
//						return ICCTrackingNameConst.ACTION_NAME_FORGOTT_LOGIN_TOUCHED;
//					}
//
//				});
//
//			}
//		});
//		UiUtility.setDefaultTypeface(loginButton);
//		loginButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				trackLoginButtonPressed();
//				if (validateLogin()) {
//					doLogin();
//				}
//			}
//		});
//
//		closeIcon.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				getActivity().setResult(Activity.RESULT_CANCELED);
//				getActivity().finish();
//			}
//		});
//
//		// //////////////////////////////////////////////////////////////////////
//		//
//		// cs7627: we need this listener to track action touched
//		//
//
//		final TrackingCheckboxRememberLogin rememberLoginTracking = new TrackingCheckboxRememberLogin();
//
//		rememberLogin.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				boolean activated = ((SwitchCompat) v).isChecked();
//				rememberLoginTracking.setCheckboxResult(activated);
//				CCTrackingAdapter.getCCTracking().trackActionForTouchedOrPressed(rememberLoginTracking);
//			}
//		}); // end inner listener for rememberLogin
//
//		// //////////////////////////////////////////////////////////////////////
//
//		final TextWatcher passwordWatcher = new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				userName = userNameText.getText().toString().trim();
//				password = passwordText.getText().toString().trim();
//			}
//		};
//
//		final TextWatcher userNameWatcher = new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				userName = userNameText.getText().toString().trim();
//				password = passwordText.getText().toString().trim();
//				// BUGFIX: DEFECT ID : 76807
//				if (!"".equalsIgnoreCase(passwordText.getText().toString())) {
//					passwordText.removeTextChangedListener(passwordWatcher);
//					passwordText.setText("");
//					passwordText.addTextChangedListener(passwordWatcher);
//				}
//
//				/*
//				 * if ((userName.length() > 0) && (password.length() > 0)) {
//				 * loginButton.setEnabled(true); } else {
//				 * loginButton.setEnabled(false); }
//				 */
//
//			}
//		};
//
//		passwordText.setOnKeyListener(new View.OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				if (event.getAction() == KeyEvent.ACTION_DOWN) {
//					switch (keyCode) {
//						case KeyEvent.KEYCODE_ENTER:
//						case KeyEvent.KEYCODE_DPAD_CENTER:
//						case KeyEvent.KEYCODE_SEARCH:
//							if (validateLogin()) {
//								doLogin();
//								return true;
//							}
//					}
//				}
//				return false;
//			}
//
//		});
//
//		userNameText.addTextChangedListener(userNameWatcher);
//		passwordText.addTextChangedListener(passwordWatcher);
//
//	}
//
//	/**
//	 * Client side validation of username and password values Login request will
//	 * send only if all these conditions got passed. Else an error dialog with
//	 * respective message will be shown to the user
//	 */
//	private boolean validateLogin() {
//
//		String nonInitialZeroUserName = userName.replaceAll("^0*", "");
//
//		if ((userName.length() < 1) && (password.length() < 1)) {
//			showLoginErrorMessage(getString(R.string.authentication_failed_please_enter_a_correct_pin_or_password),
//					getResources().getString(R.string.authentication_failed), false);
//			return false;
//		} else if ((userName.length() >= 1) && (password.length() < 1)) {
//			showLoginErrorMessage(getString(R.string.authentication_failed_please_enter_pin), getResources().getString(R.string.authentication_failed), false);
//			return false;
//
//		} else if ((userName.length() < 1) && (password.length() >= 1)) {
//			showLoginErrorMessage(getString(R.string.authentication_failed_please_enter_account_number),
//					getResources().getString(R.string.authentication_failed), false);
//			return false;
//
//		}//Allow only accounts having account with min 7 and max 10 digits
//		else if (!(nonInitialZeroUserName.length() >= MIN_USERNAME_LENGTH && nonInitialZeroUserName.length() <= MAX_USERNAME_LENGTH)) {
//			showLoginErrorMessage(getString(R.string.not_a_valid_userid_or_account_number), getResources().getString(R.string.authentication_failed), false);
//			return false;
//
//		} else if (password.length() < MIN_PASSWORD_LENGTH) {
//			showLoginErrorMessage(getString(R.string.please_enter_a_correct_pin_or_password), getResources().getString(R.string.authentication_failed), false);
//			return false;
//		}
//		return true;
//	}
//
//	private void sendLoginRequest() {
//		loginButton.setToLoadingMode();
//
//		if (CCConfig.validateRoleId) {
//			showLoadingDialog();
//		}
//
//		CCAuthenticationManager.getDefaultManager().authenticate(userName, password, new UICallBack() {
//			String errorMessage = "";
//			String title = "";
//
//			@Override
//			public void onConnectionFail(Object message) {
//				// old implementation - to be removed!
//				Log.d("onError", "onError");
//				if (message.getClass().equals(SSLPeerUnverifiedException.class)) {
//					errorMessage = getResources().getString(R.string.trust_server_certificate_message);
//					title = getResources().getString(R.string.server_certificate_not_trusted);
//					getActivity().runOnUiThread(new Runnable() {
//
//						@Override
//						public void run() {
//							trustServerCertificate(true);
//							doLogin();
//						}
//					});
//				}
//			}
//
//			@Override
//			public void onResponseReceive(final CCLoginResponse loginResponse) {
//				// TODO move to right place after removing old implementation
//				sendLoginV2Request();
//			}
//
//			@Override
//			public void onResponseFail(String message, String faultcode) {
//				// old implementation - to be remover: Haven't removed cuz of finger print implementation fail cases
//				if (getActivity() == null) {
//					return;
//				}
//				final String errorCode = faultcode;
//				final String errorMsg = message;
//
//				disableFingerPrint();
//
//				getActivity().runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						if (isUserRemembered() && !CCSharedPreferencUtils.getBooleanPref(CCCoreConst.SETTINGS.FINGERPRINT_LOGIN_ACTIVATED)) {
//							userNameText.setText(CCSharedPreferencUtils.getPref(AUTHENTICATIONMODULE.CREDENTIAL_USERNAME));
//						}
//						loginButton.setToNormalMode();
//						showLoginStatus(true);
//						if (errorCode.equalsIgnoreCase(LOGIN_ERROR_FAULT_CODE_0) || errorCode.equalsIgnoreCase(LOGIN_ERROR_FAULT_CODE_1)
//								|| errorCode.equalsIgnoreCase(LOGIN_ERROR_FAULT_CODE_2) || errorCode.equalsIgnoreCase(LOGIN_ERROR_FAULT_CODE_3)) {
//							showLoginErrorMessage(errorMsg, getResources().getString(R.string.login_not_possible), true);
//						} else {
//							showLoginErrorMessage(errorMsg, getResources().getString(R.string.login_not_possible), false);
//						}
//
//						// Clear password field after login failure message
//						if (passwordText != null) {
//							passwordText.setText("");
//						}
//					}
//				});
//			}
//		});
//
//	}
//
//	private void disableFingerPrint() {
//		CBCryptographyHelper.deleteKeyStore(getContext());
//	}
//
//	// Login request using Diffusion
//	private void sendLoginV2Request() {
//		CCAuthenticationManager.getDefaultManager().authenticateV2(userName, password, new CCLoginFacade.UICallBackForV2() {
//			final TrackingLoginWebserviceResult trackingLoginWebservice = new TrackingLoginWebserviceResult();
//			String errorMessage = "";
//			String title = "";
//
//			@Override
//			public void onResponseReceive(CBLoginResponse loginResponse) {
//				if (getActivity() == null) {
//					return;
//				}
//				//To check whether the device is rooted and have fingerprint
//				// we don't have to restrict a rooted phone without fingerprint.
//				boolean isRootedWithFingerprint = false;
//
//				//Checks whether device have fingerprint and Loggin in for the first time.
//				if (CCConfigBase.sFingerprintCompatibleDevice && !CCSharedPreferencUtils.getBooleanPref(CCCoreConst.SETTINGS.FINGERPRINT_LOGIN_ACTIVATED)) {
//					//Show fingerprint pop-up for normal device and rooted device pop-up for rooted phones.
//					if (!CBRootDetectionHelper.isRooted()) {
//						isRootedWithFingerprint = false;
//						CCSharedPreferencUtils.setPref(AUTHENTICATIONMODULE.FINGERPRINT_CREDENTIAL_USERNAME, userName);
//						try {
//							CCLoginCredentials.setPassword(CBCryptographyHelper.encryptUserCredential(password, getActivity()));
//						} catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException |
//								IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchPaddingException e) {
//							e.printStackTrace();
//						}
//						if (CCSharedPreferencUtils.getBooleanPref(AUTHENTICATIONMODULE.IS_LOGGING_IN_FOR_FIRST_TIME, true)) {
//							CCSharedPreferencUtils.setBooleanPref(AUTHENTICATIONMODULE.IS_LOGGING_IN_FOR_FIRST_TIME, false);
//							showFingerprintActivatePopup();
//							return;
//						}
//					} else {
//						//Checks whether the Rooted device pop up has shown or not.
//						if (!CCSharedPreferencUtils.getBooleanPref(AUTHENTICATIONMODULE.IS_ROOTED_DEVICE_POPUP_SHOWN)) {
//							isRootedWithFingerprint = true;
//							showRootedDevicePopup();
//						}
//					}
//				}
//				//				if (CCConfig.validateRoleId) {
//				//					mAccountFacade.getAccountetails(CCLoginFragment.this);
//				//				} else {
//				//If pop up is not shown and, device is rooted with fingerprint feature
//				//Login is not proceeded, untill he accepts the pop-up message.
//				if (!isRootedWithFingerprint) {
//					completeLoginProcess();
//				}
//				//				}
//			}
//
//			@Override
//			public void onResponseFail(String message, String faultcode) {
//				if (getActivity() == null) {
//					return;
//				}
//				final String errorCode = faultcode;
//				final String errorMsg = message;
//
//				disableFingerPrint();
//
//				/**
//				 * CCLogger.e(this, "Login Error faultcode = "+faultcode);
//				 * CCLogger.e(this, "Login Error Message = "+errorMsg);
//				 */
//				getActivity().runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						if (isUserRemembered() && !CCSharedPreferencUtils.getBooleanPref(CCCoreConst.SETTINGS.FINGERPRINT_LOGIN_ACTIVATED)) {
//							userNameText.setText(CCSharedPreferencUtils.getPref(AUTHENTICATIONMODULE.CREDENTIAL_USERNAME));
//						}
//						loginButton.setToNormalMode();
//						showLoginStatus(true);
//						if (errorCode != null && errorMsg != null) {
//							if (errorCode.equalsIgnoreCase(LOGIN_ERROR_FAULT_CODE_0) || errorCode.equalsIgnoreCase(LOGIN_ERROR_FAULT_CODE_1)
//									|| errorCode.equalsIgnoreCase(LOGIN_ERROR_FAULT_CODE_2) || errorCode.equalsIgnoreCase(LOGIN_ERROR_FAULT_CODE_3)) {
//								showLoginErrorMessage(errorMsg, getResources().getString(R.string.login_not_possible), true);
//							} else {
//								showLoginErrorMessage(errorMsg, getResources().getString(R.string.login_not_possible), false);
//							}
//
//							// Clear password field after login failure message
//							if (passwordText != null) {
//								passwordText.setText("");
//							}
//							// showLoginFailMessage(errorMsg);
//
//							// tracking failure action on login response
//							final StringBuilder b = new StringBuilder();
//							b.append(errorCode).append(" ").append(errorMsg);
//							trackingLoginWebservice.setActionName(ICCTrackingNameConst.ACTION_NAME_LOGIN_FAILURE);
//							trackingLoginWebservice.setLoginResponseResult(b.toString());
//							CCTrackingAdapter.getCCTracking().trackActionForWebservice(trackingLoginWebservice);
//						}
//					}
//				});
//			}
//
//			@Override
//			public void onError(Object message) {
//				CCLogger.w(this, "onError ::: " + message);
//				if (getActivity() != null) {
//					if (message != null && message.getClass().equals(SSLPeerUnverifiedException.class)) {
//						errorMessage = getResources().getString(R.string.trust_server_certificate_message);
//						title = getResources().getString(R.string.server_certificate_not_trusted);
//						getActivity().runOnUiThread(new Runnable() {
//
//							@Override
//							public void run() {
//								loginButton.setToNormalMode();
//								showLoginStatus(true);
//								showTrustCertificateDialog(title, errorMessage);
//							}
//						});
//					} else {
//						getActivity().runOnUiThread(new Runnable() {
//
//							@Override
//							public void run() {
//								errorMessage = getResources().getString(R.string.connection_failed);
//								loginButton.setToNormalMode();
//								showLoginStatus(true);
//								CCUiAlertUtility.showLongTimeToast(errorMessage);
//							}
//						});
//					}
//
//					// tracking failure action on login response
//					trackingLoginWebservice.setActionName(ICCTrackingNameConst.ACTION_NAME_LOGIN_FAILURE);
//					trackingLoginWebservice.setLoginResponseResult(errorMessage);
//					CCTrackingAdapter.getCCTracking().trackActionForWebservice(trackingLoginWebservice);
//				}
//			}
//		});
//	}
//
//	private void doLogin() {
//		//TODO://Remove old and use sendLoginV2Request() when old implementation is entirely not needed.
//		if (mPermissionHelper.hasPermisson(Manifest.permission.READ_PHONE_STATE)) {
//			CCKeyBoardStatus.hideKeyBoard(getActivity());
//			showLoginStatus(false);
//			sendLoginRequest();
//		} else {
//			mPermissionHelper.requestPermission(Manifest.permission.READ_PHONE_STATE);
//		}
//		// Login using Diffusion
//	}
//
//	@Subscribe(threadMode = ThreadMode.MAIN)
//	public void onEventMainThread(CCSystemErrorEvent systemErrorEvent) {
//		if (systemErrorEvent.isSenderEqualTo(this)) {
//			dismissLoadingDialog();
//			loginButton.setToNormalMode();
//			showLoginStatus(true);
//			showLoginErrorMessage(getString(R.string.no_data_is_currently_available_try_again), getResources().getString(R.string.authentication_failed),
//					false);
//		}
//	}
//
//	@Subscribe(threadMode = ThreadMode.MAIN)
//	public void onEventMainThread(CCServiceErrorEvent serviceErrorEvent) {
//		if (serviceErrorEvent.isSenderEqualTo(this)) {
//			dismissLoadingDialog();
//			loginButton.setToNormalMode();
//			showLoginStatus(true);
//			showLoginErrorMessage(getString(R.string.no_data_is_currently_available_try_again), getResources().getString(R.string.authentication_failed),
//					false);
//		}
//	}
//
//	//	@Subscribe(threadMode = ThreadMode.MAIN)
//	//	public void onEventMainThread(CBAccountInfoEvent accountInfoEvent) {
//	//		String message = CCConfig.validateRoleId ? getString(R.string.non_wm_customer_message) : getString(R.string.not_a_valid_userid_or_account_number);
//	//		String title = CCConfig.validateRoleId ? getString(R.string.information_info) : getResources().getString(R.string.authentication_failed);
//	//		if (accountInfoEvent.isSenderEqualTo(this) && accountInfoEvent.response instanceof CBAccountInfoResponse) {
//	//			CBAccountInfoResponse response = (CBAccountInfoResponse) accountInfoEvent.response;
//	//			if (response.isWMRole() && CCLoginCredentials.haveValidSession()) {
//	//				// continue login here
//	//				completeLoginProcess();
//	//
//	//			} else {
//	//				CCAuthenticationManager.invalidateUserSession();
//	//				loginButton.setToNormalMode();
//	//				if (passwordText != null) {
//	//					passwordText.setText("");
//	//				}
//	//				showLoginStatus(true);
//	//				showLoginErrorMessage(message, title, false);
//	//			}
//	//			dismissLoadingDialog();
//	//		}
//	//	}
//
//	private void proceedLogin() {
//		final TrackingLoginWebserviceResult trackingLoginWebservice = new TrackingLoginWebserviceResult();
//		getActivity().runOnUiThread(new Runnable() {
//
//			@Override
//			public void run() {
//				rememberCredentials();
//				loginButton.setToNormalMode();
//				showLoginStatus(true);
//				// notifiyLogIn();
//				// CCAuthenticationGlobal.updateUIChanges();
//				if (!isParallexLogin) {
//					getActivity().setResult(Activity.RESULT_OK, getActivity().getIntent());
//					getActivity().finish();
//				}
//				//Saving crm customer no for tracking if user is not logged in.
//				if (!StringUtils.isEmpty(CCLoginCredentials.getCrmCustomerNo())) {
//					CCSharedPreferencUtils.setPref(CCCoreConst.SHARED_PREFS_KEY.CRM_CUSTOMER_NO, CCLoginCredentials.getCrmCustomerNo());
//				}
//
//				// tracking success action on login response
//				trackingLoginWebservice.setActionName(ICCTrackingNameConst.ACTION_NAME_LOGIN_SUCCESS);
//				trackingLoginWebservice.setLoginResponseResult(ICCTrackingNameConst.EMPTY_ENTRY);
//				CCTrackingAdapter.getCCTracking().trackActionForLogin(trackingLoginWebservice);
//			}
//		});
//	}
//
//	private void rememberCredentials() {
//		boolean shouldRemember = rememberLogin.isChecked();
//		CCSharedPreferencUtils.setPref(AUTHENTICATIONMODULE.CREDENTIAL_USERNAME, shouldRemember ? userName : "");
//		CCSharedPreferencUtils.setBooleanPref(AUTHENTICATIONMODULE.CREDENTIAL_REMEMBER, shouldRemember);
//	}
//
//	/*
//	 * Show or hide login status
//	 *
//	 * @param hide true to hide, false to show
//	 */
//	private void showLoginStatus(boolean show) {
//		loginButton.setEnabled(show);
//		rememberLogin.setEnabled(show);
//		userNameText.setEnabled(show);
//		passwordText.setEnabled(show);
//
//	}
//
//	private void loadRememberedCredentails() {
//		String savedUserName = CCSharedPreferencUtils.getPref(AUTHENTICATIONMODULE.CREDENTIAL_USERNAME);
//		String fingerPrintUserName = CCSharedPreferencUtils.getPref(AUTHENTICATIONMODULE.FINGERPRINT_CREDENTIAL_USERNAME);
//		// boolean for checking remembered credential and finger credential
//		boolean isCredentialSame = savedUserName.equals(fingerPrintUserName) ? true : false;
//		boolean userNameRememberCheck = CCSharedPreferencUtils.getBooleanPref(AUTHENTICATIONMODULE.CREDENTIAL_REMEMBER);
//		if (userNameRememberCheck
//				&& (isCredentialSame || !CCSharedPreferencUtils.getBooleanPref(CCCoreConst.SETTINGS.FINGERPRINT_LOGIN_ACTIVATED))) {
//			userNameText.setText(savedUserName);
//			rememberLogin.setChecked(true);
//		} else if (CCSharedPreferencUtils.getBooleanPref(CCCoreConst.SETTINGS.FINGERPRINT_LOGIN_ACTIVATED)) {
//			userNameText.setText(fingerPrintUserName);
//			rememberLogin.setChecked(false);
//		} else {
//			userNameText.setText("");
//			rememberLogin.setChecked(false);
//		}
//	}
//
//	/**
//	 * It will set the focus for the Edit text
//	 */
//	public void setRequestFocus() {
//		final String user = userNameText != null ? userNameText.getText().toString().trim() : "";
//		if (isUserRemembered() && (user.length() > 0)) {
//			if (passwordText != null) {
//				passwordText.requestFocus();
//			}
//		} else {
//			if (userNameText != null) {
//				userNameText.requestFocus();
//			}
//		}
//	}
//
//	public void showFingerPrintDialog() {
//		// Show fingerprint pop-up for normal device and rooted device pop-up for rooted phones.
//		// Both permission are given in case the app settings is changed in between, both permission are required for finger print
//		if (!isDenyPopupVisible) {
//			if (mPermissionHelper.hasPermisson(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)) {
//				if (!CBRootDetectionHelper.isRooted()) {
//					if (!isFPEnableConfirmPopupVisible) {
//						CBFingerPrintUIHelper.showPopup(this, getActivity().getResources().getString(R.string.fingerprint_model_content),
//								getResources().getString(R.string.fingerprint_button));
//					}
//				} else {
//					if (!CCSharedPreferencUtils.getBooleanPref(AUTHENTICATIONMODULE.IS_ROOTED_DEVICE_POPUP_SHOWN)) {
//						showRootedDevicePopup();
//					} else {
//						completeLoginProcess();
//					}
//				}
//			} else {
//				mPermissionHelper.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);
//			}
//		}
//	}
//
//	private boolean isUserRemembered() {
//		String savedUserName = CCSharedPreferencUtils.getPref(AUTHENTICATIONMODULE.CREDENTIAL_USERNAME);
//		return savedUserName.length() > 0;
//	}
//
//	//	public void notifiyLogIn() {
//	//		// CCAuthenticationGlobal.updateUIChanges();
//	//		// CCAuthenticationManager.notifiyLogIn();
//	//	}
//
//	private void trustServerCertificate(boolean trust) {
//		CCConfig.setServerTrusted(trust);
//	}
//
//	private void showTrustCertificateDialog(String title, String message) {
//		String positiveButtonMsg = getString(R.string.ok);
//		String negativeButtonMsg = getString(R.string.cancel);
//		DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				trustServerCertificate(true);
//				doLogin();
//			}
//		};
//
//		DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				trustServerCertificate(false);
//				dialog.dismiss();
//			}
//		};
//		CCDialogModel modal = new CCDialogModel(title, message, positiveButtonMsg, positiveListener, negativeButtonMsg, negativeListener,
//				CCDialogModel.CUSTOM_CONFIRM_DIALOG, false);
//		CCDialogFragment.newInstance(modal).show(getChildFragmentManager(), "");
//	}
//
//	/**
//	 * Error dialog for login screen. This dialog will popup if login validation
//	 * criteria or login response fails Some error dialogs will have additional
//	 * controls enabling user to contact personal care team or visit website
//	 * www.cortalconsors.de
//	 */
//	private void showLoginErrorMessage(String message, String title, boolean isSpecialFaultCode) {
//		hideKeyboard();
//
//		String negativeButtonMsg = getResources().getString(R.string.call);
//		String neutralButtonMsg = getResources().getString(R.string.website);
//		String positiveButtonMsg = getResources().getString(R.string.ok);
//
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//		alertDialogBuilder.setTitle(title);
//		alertDialogBuilder.setMessage(message);
//		alertDialogBuilder.setCancelable(true);
//		alertDialogBuilder.setIcon(R.drawable.ic_launcher);
//
//		// Dialog cancel button
//		alertDialogBuilder.setPositiveButton(positiveButtonMsg, new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//
//		/**
//		 * Check for special error messages. This is for specific error code
//		 * which we get from server.
//		 */
//		if (isSpecialFaultCode) {
//			/**
//			 * We don't show fault code along with pin status to the user in
//			 * these special cases So separate this errorCode from message
//			 */
//			if (message.contains("# ")) {
//				alertDialogBuilder.setMessage(message.split("\\# ")[1]);
//			}
//
//			/**
//			 * Call Button Call goes to cortal consors personal care team
//			 */
//			alertDialogBuilder.setNegativeButton(negativeButtonMsg, new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					dialog.cancel();
//					if (mPermissionHelper.hasPermisson(Manifest.permission.CALL_PHONE)) {
//						callCustomerSupport();
//					} else {
//						mPermissionHelper.requestPermission(Manifest.permission.CALL_PHONE);
//					}
//				}
//			});
//
//			/**
//			 * Website Button OnClick load cortal consors website
//			 */
//			alertDialogBuilder.setNeutralButton(neutralButtonMsg, new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//
//					Intent intent = new Intent(getActivity(), CCPopupActivity.class);
//					intent.putExtra(CCPopupActivity.CONTENT_TYPE, CCPopUpContentTypeEnum.INAPP_BROWSER);
//					Bundle arguments = new Bundle();
//					arguments.putString(CCCommonWebViewFragment.URL, getResources().getString(R.string.website_url));
//					intent.putExtras(arguments);
//					startActivity(intent);
//					if (CCConfig.isTablet()) {
//						getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//					}
//					dialog.cancel();
//				}
//			});
//
//		}
//		AlertDialog dialog = alertDialogBuilder.create();
//		dialog.show();
//
//		/**
//		 * When dialog dismiss, login fields get focus
//		 */
//		dialog.setOnCancelListener(new OnCancelListener() {
//			@Override
//			public void onCancel(DialogInterface dialog) {
//				showKeyboard();
//			}
//		});
//
//	}
//
//	private void callCustomerSupport() {
//		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getResources().getString(R.string.contact_personal_care_team)));
//		startActivity(intent);
//	}
//
//	private void setFocusable(boolean value) {
//		userNameText.setFocusable(value);
//		passwordText.setFocusable(value);
//
//		userNameText.setFocusableInTouchMode(value);
//		passwordText.setFocusableInTouchMode(value);
//	}
//
//	@Override
//	public void hideKeyboard() {
//		setFocusable(false);
//		CCKeyBoardStatus.hideKeyBoard(rootView);
//
//	}
//
//	@Override
//	public void showKeyboard() {
//		setFocusable(true);
//		CCKeyBoardStatus.showKeyBoardForView(userNameText);
//
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		// tracking if we do not come from login section in parallax fragment
//		CCPopUpContentTypeEnum contentType = (CCPopUpContentTypeEnum) getArguments().getSerializable(CCPopupActivity.CONTENT_TYPE);
//		if (contentType == CCPopUpContentTypeEnum.LOGIN) {
//			// tracking screen
//			if (!isClearingBackStack()) {
//				CCTrackingAdapter.getCCTracking().trackScreen(this);
//			}
//
//		}
//		mEventBus.register(this);
//		addSecuredListener(this);
//
//		if (mPendingPermissionDialogStatus == DSPermissionsHelper.PERMISSION_GRANTED) {
//			handlePermissionGranted(mPermissions);
//			mPermissions = null;
//			mPendingPermissionDialogStatus = -1;
//		} else if (mPendingPermissionDialogStatus == DSPermissionsHelper.PERMISSION_DENIED) {
//			handlePermissionDenied(mPermissions);
//			mPermissions = null;
//			mPendingPermissionDialogStatus = -1;
//		}
//	}
//
//	@Override
//	public void onPause() {
//		super.onPause();
//		mEventBus.unregister(this);
//		removeSecuredListener(this);
//		CCKeyBoardStatus.hideKeyBoard(rootView);
//
//	}
//
//	@Override
//	public String getBusinessNameOfScreen() {
//		return ICCTrackingNameConst.SECTION_NAME_LOGIN;
//	}
//
//	@Override
//	public String getSectionNameOfScreen() {
//		return ICCTrackingNameConst.SECTION_NAME_LOGIN;
//	}
//
//	// //////////////////////////////////////////////////////////////////////////
//	//
//	// tracking helper classes
//
//	@Override
//	public void onLogIn() {
//		if (getActivity() != null && passwordText != null) {
//			resetPassword();
//		}
//	}
//
//	;
//
//	@Override
//	public void onLogOut() {
//		mFingerprintLoginSuccess = false;
//		resetPassword();
//	}
//
//	/**
//	 * Clear password field on login or logout
//	 */
//	private void resetPassword() {
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				if (passwordText != null) {
//					passwordText.setText("");
//				}
//
//			}
//		});
//	}
//
//	// popup for deny message
//	private void showDenyMessageDialog(final String permission, CBDisplayMessage displayMessage, final boolean multiplePermission,
//									   final boolean showRationale) {
//		isFPEnableConfirmPopupVisible = false;
//		isDenyPopupVisible = true;
//		final CCCommonMessageDialog commonMessageDialog = CCCommonMessageDialog.newInstance();
//		commonMessageDialog.setArguments(displayMessage, 0, false);
//		if (CCConfig.isTablet()) {
//			commonMessageDialog.setWidthHeight((UiUtility.getDisplayWidth() * 2) / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
//		} else {
//			commonMessageDialog.setWidthHeight(UiUtility.getDisplayWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
//		}
//		commonMessageDialog.show(getChildFragmentManager(), "errorDialog");
//		commonMessageDialog.setOnDialogBackPressed(new CCCustomDialog.onDialogBackPressedListener() {
//			@Override
//			public void onDialogBackPressed() {
//				if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && !multiplePermission) {
//					completeLoginProcess();
//				}
//				commonMessageDialog.dismiss();
//				isDenyPopupVisible = false;
//			}
//		});
//		commonMessageDialog.setOnMessageDismissClickListner(new CCCommonMessageDialog.onMessgeDismissClickListener() {
//			@Override
//			public void onMessageDismissClick(int buttonID) {
//				if (buttonID == CBDisplayButton.NEGATIVE_BUTTON) {
//					if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && !multiplePermission) {
//						completeLoginProcess();
//					}
//					isDenyPopupVisible = false;
//					commonMessageDialog.dismiss();
//				} else if (buttonID == CBDisplayButton.POSITIVE_BUTTON) {
//					isDenyPopupVisible = false;
//					commonMessageDialog.dismiss();
//					if (showRationale) {
//						if (multiplePermission) {
//							mPermissionHelper.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);
//						} else {
//							if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//								mPermissionHelper.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//							} else if (permission.equals(Manifest.permission.READ_PHONE_STATE)) {
//								mPermissionHelper.requestPermission(Manifest.permission.READ_PHONE_STATE);
//							} else if (permission.equals(Manifest.permission.CALL_PHONE)) {
//								mPermissionHelper.requestPermission(Manifest.permission.CALL_PHONE);
//							}
//						}
//					} else {
//						completeLoginProcess();
//						Intent intent = new Intent();
//						intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//						Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
//						intent.setData(uri);
//						startActivity(intent);
//					}
//				}
//			}
//		});
//	}
//
//	//show confirmation popup
//	private void showFingerprintActivatePopup() {
//		isFPEnableConfirmPopupVisible = true;
//		final CCCommonMessageDialog commonMessageDialog = CCCommonMessageDialog.newInstance();
//		List<CBDisplayButton> buttons = new ArrayList<CBDisplayButton>();
//		buttons.add(new CBDisplayButton(getResources().getString(R.string.no), CBDisplayButtonAction.IGNORE, CBDisplayButton.NEGATIVE_BUTTON));
//		buttons.add(new CBDisplayButton(getResources().getString(R.string.finger_touch_confirmation_ok), CBDisplayButtonAction.IGNORE,
//				CBDisplayButton.POSITIVE_BUTTON));
//		CBDisplayMessage displayMessage = new CBDisplayMessage(getResources().getString(R.string.finger_touch_enable_heading),
//				getResources().getString(R.string.finger_touch_confirmation_msg), false, buttons);
//		commonMessageDialog.setArguments(displayMessage, 0, false);
//		if (CCConfig.isTablet()) {
//			commonMessageDialog.setWidthHeight((UiUtility.getDisplayWidth() * 2) / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
//		} else {
//			commonMessageDialog.setWidthHeight(UiUtility.getDisplayWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
//		}
//		commonMessageDialog.show(getChildFragmentManager(), "errorDialog");
//		commonMessageDialog.setOnDialogBackPressed(new CCCustomDialog.onDialogBackPressedListener() {
//			@Override
//			public void onDialogBackPressed() {
//				completeLoginProcess();
//			}
//		});
//		commonMessageDialog.setOnMessageDismissClickListner(new CCCommonMessageDialog.onMessgeDismissClickListener() {
//			@Override
//			public void onMessageDismissClick(int buttonID) {
//				if (buttonID == CBDisplayButton.NEGATIVE_BUTTON) {
//					commonMessageDialog.dismiss();
//					showFingerPrintResultPopup(false);
//				} else if (buttonID == CBDisplayButton.POSITIVE_BUTTON) {
//					commonMessageDialog.dismiss();
//					if (mPermissionHelper.hasPermisson(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//						CCSharedPreferencUtils.setPref(AUTHENTICATIONMODULE.FINGERPRINT_CREDENTIAL_PASSWORD,
//								CCLoginCredentials.getPassword());
//						CCSharedPreferencUtils.setBooleanPref(CCCoreConst.SETTINGS.FINGERPRINT_LOGIN_ACTIVATED, true);
//						showFingerPrintResultPopup(true);
//					} else {
//						mPermissionHelper.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//					}
//				}
//			}
//		});
//	}
//
//	//show popup after enabling or disabling the fingerprint from confirmation popup
//	private void showFingerPrintResultPopup(boolean isFingerprintEnabled) {
//		isFPEnableConfirmPopupVisible = true;
//		CBDisplayMessage displayMessage = null;
//		String OK = ApplicationContextHolder.getAppContext().getResources().getString(R.string.ok);
//		final CCCommonMessageDialog commonMessageDialog = CCCommonMessageDialog.newInstance();
//		List<CBDisplayButton> buttons = new ArrayList<CBDisplayButton>();
//		buttons.add(new CBDisplayButton(OK, CBDisplayButtonAction.IGNORE, CBDisplayButton.POSITIVE_BUTTON));
//		if (isFingerprintEnabled) {
//			displayMessage = new CBDisplayMessage(getResources().getString(R.string.fingerprint_enabled_success_header),
//					getResources().getString(R.string.fingerprint_enabled_ok_msg), false, buttons);
//		} else {
//			displayMessage = new CBDisplayMessage(getResources().getString(R.string.warning),
//					getResources().getString(R.string.fingerprint_enabled_cancel_msg), false, buttons);
//		}
//		commonMessageDialog.setArguments(displayMessage, 0, false);
//		if (CCConfig.isTablet()) {
//			commonMessageDialog.setWidthHeight((UiUtility.getDisplayWidth() * 2) / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
//		} else {
//			commonMessageDialog.setWidthHeight(UiUtility.getDisplayWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
//		}
//		commonMessageDialog.show(getChildFragmentManager(), "resultDialog");
//		commonMessageDialog.setOnDialogBackPressed(new CCCustomDialog.onDialogBackPressedListener() {
//			@Override
//			public void onDialogBackPressed() {
//				completeLoginProcess();
//			}
//		});
//		commonMessageDialog.setOnMessageDismissClickListner(new CCCommonMessageDialog.onMessgeDismissClickListener() {
//			@Override
//			public void onMessageDismissClick(int buttonID) {
//				if (buttonID == CBDisplayButton.POSITIVE_BUTTON) {
//					commonMessageDialog.dismiss();
//					//					if (CCConfig.validateRoleId) {
//					//						mAccountFacade.getAccountDetails(CCLoginFragment.this);
//					//					} else {
//					completeLoginProcess();
//					//					}
//				}
//			}
//		});
//	}
//
//	/**
//	 * If the device is rooted, fingerprint login is not supported.
//	 * This is a pop up to the user saying the device is rooted.
//	 * User has to accept the message, and it will only shown once.
//	 */
//	private void showRootedDevicePopup() {
//		//Disable fingerprint features.
//		disableFingerPrint();
//		//Set pop-up shown value to true.
//		CCSharedPreferencUtils.setBooleanPref(AUTHENTICATIONMODULE.IS_ROOTED_DEVICE_POPUP_SHOWN, true);
//
//		CCSimpleDialogFragment dialog = CCSimpleDialogFragment.newInstance();
//		dialog.setTitle(getString(R.string.fingerprint_rooted_device_title));
//		dialog.setMessage(getString(R.string.fingerprint_rooted_device_message));
//		dialog.setPositiveButton(getString(R.string.ok));
//		dialog.enableButtonClickCallbacks();
//		dialog.setTargetFragment(this, 1);
//		dialog.setCancelable(false);
//		dialog.show(getFragmentManager(), DIALOG_TAG);
//	}
//
//	/**
//	 * Proceed login process in device
//	 */
//	private void completeLoginProcess() {
//		proceedLogin();
//		mWLMediator.clearWatchlistCache();
//		authManager.notifiyLogIn();
//	}
//
//	/**
//	 * For tracking login button pressed.
//	 */
//	private void trackLoginButtonPressed() {
//		// track action delete security
//		CCTrackingAdapter.getCCTracking().trackActionForTouchedOrPressed(new ICCTrackingActionDataForTouchedOrPressed() {
//
//			@Override
//			public String getBusinessNameOfScreen() {
//				return CCLoginFragment.this.getBusinessNameOfScreen();
//			}
//
//			@Override
//			public String getBusinessNameOfAction() {
//				return ICCTrackingNameConst.ACTION_NAME_LOGIN_TOUCHED;
//			}
//
//		});
//	}
//
//	@Override
//	public void onSuccessFingerPrint() {
//		mFingerprintLoginSuccess = true;
//		userName = CCSharedPreferencUtils.getPref(AUTHENTICATIONMODULE.FINGERPRINT_CREDENTIAL_USERNAME);
//		try {
//			password = CBCryptographyHelper.decryptUserCredential
//					(CCSharedPreferencUtils.getPref(AUTHENTICATIONMODULE.FINGERPRINT_CREDENTIAL_PASSWORD), getActivity());
//		} catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException | IllegalBlockSizeException |
//				InvalidKeyException | BadPaddingException | NoSuchPaddingException | UnrecoverableKeyException e) {
//			e.printStackTrace();
//		}
//		if (validateLogin()) {
//			doLogin();
//		}
//	}
//
//	@Override
//	public void onErrorFingerPrint(String errorString) {
//		// nothing to be done
//		mFingerprintLoginSuccess = false;
//	}
//
//	@Override
//	public void permissionGranted(String[] permissions) {
//		if (mSavedInstanceCalled) {
//			mPendingPermissionDialogStatus = DSPermissionsHelper.PERMISSION_GRANTED;
//			this.mPermissions = permissions;
//			return;
//		}
//		handlePermissionGranted(permissions);
//	}
//
//	@Override
//	public void permissionDenied(String[] permissions) {
//		if (mSavedInstanceCalled) {
//			mPendingPermissionDialogStatus = DSPermissionsHelper.PERMISSION_DENIED;
//			this.mPermissions = permissions;
//			return;
//		}
//		handlePermissionDenied(permissions);
//	}
//
//	private void handlePermissionDenied(String[] permissions) {
//		CBDisplayMessage displayMessage = null;
//		for (String permission : permissions) {
//			// in case don't show again is selected, show rationale returns false
//			boolean showRationale = shouldShowRequestPermissionRationale(permission);
//			List<CBDisplayButton> buttons = new ArrayList<CBDisplayButton>();
//			buttons.add(new CBDisplayButton(getString(R.string.cancel), CBDisplayButtonAction.IGNORE, CBDisplayButton.NEGATIVE_BUTTON));
//			if (showRationale) {
//				buttons.add(new CBDisplayButton(getString(R.string.ok), CBDisplayButtonAction.IGNORE, CBDisplayButton.POSITIVE_BUTTON));
//			} else {
//				buttons.add(new CBDisplayButton(getString(R.string.settings), CBDisplayButtonAction.IGNORE, CBDisplayButton.POSITIVE_BUTTON));
//			}
//			if (!mPermissionHelper.hasPermisson(permission)) {
//				if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && showRationale) {
//					displayMessage = new CBDisplayMessage(getString(R.string.external_permission_deny_msg_header),
//							getString(R.string.external_permission_deny_msg), false, buttons);
//				} else if (permission.equals(Manifest.permission.READ_PHONE_STATE) && showRationale) {
//					displayMessage = new CBDisplayMessage(getString(R.string.read_phone_state_permission_deny_msg_header),
//							getString(R.string.read_phone_state_permission_deny_msg), false, buttons);
//				} else if (permission.equals(Manifest.permission.CALL_PHONE) && showRationale) {
//					// As per current implementation, this permission will already be granted before login request is made. So this case will not occur.
//					// If we modify SEP login process later, this message will be required.
//					displayMessage = new CBDisplayMessage("HEADING FOR CALL", "Call feature need this permission", false, buttons);
//				} else {
//					displayMessage = new CBDisplayMessage("", getString(R.string.text_when_dont_ask_again_selected_and_permission_denied), false, buttons);
//				}
//			}
//			showDenyMessageDialog(permission, displayMessage, ((permissions.length > 1) ? true : false), showRationale);
//		}
//	}
//
//	private void handlePermissionGranted(String[] permissions) {
//		// permission length is checked in case both permission are changed after it was given initially
//		if (!(permissions.length > 1) && permissions[0].equals(Manifest.permission.CALL_PHONE)) {
//			callCustomerSupport();
//		} else if (!(permissions.length > 1) && permissions[0].equals(Manifest.permission.READ_PHONE_STATE)) {
//			if (validateLogin()) {
//				doLogin();
//			}
//		} else if (!(permissions.length > 1) && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//			if (CCLoginCredentials.getPassword() != null) {
//				CCSharedPreferencUtils.setPref(AUTHENTICATIONMODULE.FINGERPRINT_CREDENTIAL_PASSWORD, CCLoginCredentials.getPassword());
//				CCSharedPreferencUtils.setBooleanPref(CCCoreConst.SETTINGS.FINGERPRINT_LOGIN_ACTIVATED, true);
//				showFingerPrintResultPopup(true);
//			}
//		} else {
//			showFingerPrintDialog();
//		}
//	}
//
//	@Override
//	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//		mPermissionHelper.handleRequestPermissionResult(requestCode, permissions, grantResults);
//	}
//
//	/**
//	 * Rooted device Pop-up message call back.
//	 */
//	@Override
//	public void onPositiveButtonClick() {
//		//Complete the remaining login processes.
//		completeLoginProcess();
//	}
//
//	@Override
//	public void onNegativeButtonClick() {
//
//	}
//
//	/**
//	 * helper class for tracking login web service of success or failure
//	 *
//	 * @author cs7627
//	 */
//	private final class TrackingLoginWebserviceResult implements CCTrackingActionDataForLogin {
//
//		private String result = ICCTrackingNameConst.UNKNOWN_ENTRY;
//		private String actionName = ICCTrackingNameConst.UNKNOWN_ENTRY;
//
//		public void setActionName(String actionName) {
//			this.actionName = actionName;
//		}
//
//		public void setLoginResponseResult(String msg) {
//			result = msg;
//		}
//
//		@Override
//		public String getBusinessNameOfScreen() {
//			return CCLoginFragment.this.getBusinessNameOfScreen();
//		}
//
//		@Override
//		public String getBusinessNameOfAction() {
//			return actionName;
//		}
//
//		@Override
//		public String getServiceResponseResult() {
//			return result;
//		}
//
//		@Override
//		public String getUsedAuthenticationMethod() {
//			if (mFingerprintLoginSuccess) {
//				return ICCTrackingNameConst.CCUSER_LOGIN_TOUCHID;
//			}
//			return ICCTrackingNameConst.CCUSER_LOGIN_ACCNO_PASS;
//		}
//	}
//
//	/**
//	 * helper class for tracking the state of the checkbox rememberLogin
//	 *
//	 * @author cs7627
//	 */
//	private final class TrackingCheckboxRememberLogin implements ICCTrackingActionDataForTouchedOrPressed {
//
//		private String result = ICCTrackingNameConst.UNKNOWN_ENTRY;
//
//		public void setCheckboxResult(boolean activated) {
//			if (activated) {
//				result = ICCTrackingNameConst.ACTION_NAME_SAVE_ACCOUNT_NUMBER_ACTIVATED;
//			} else {
//				result = ICCTrackingNameConst.ACTION_NAME_SAVE_ACCOUNT_NUMBER_DEACTIVATED;
//			}
//		}
//
//		@Override
//		public String getBusinessNameOfScreen() {
//			return CCLoginFragment.this.getBusinessNameOfScreen();
//		}
//
//		@Override
//		public String getBusinessNameOfAction() {
//			return result;
//		}
//
//	}
//
//	// //////////////////////////////////////////////////////////////////////////
//
//}
