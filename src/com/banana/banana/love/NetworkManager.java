package com.banana.banana.love; 

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.util.Log;

import com.banana.banana.LogoutResponse;
import com.banana.banana.MyApplication;
import com.banana.banana.WithDrawReponse;
import com.banana.banana.dday.DdayResponse;
import com.banana.banana.dday.DdayResult;
import com.banana.banana.login.AutoLoginResponse;
import com.banana.banana.login.LoginResult;
import com.banana.banana.main.CoupleInfoResult;
import com.banana.banana.main.UserInfoResponse;
import com.banana.banana.main.UserInfoResult;
import com.banana.banana.mission.BananaItemResponse;
import com.banana.banana.mission.MissionIngResult;
import com.banana.banana.mission.MissionResult;
import com.banana.banana.setting.NoticeResponse;
import com.banana.banana.setting.WomanInfoResponse;
import com.banana.banana.signup.JoinResult;
import com.banana.banana.signup.WomanInfoParcelData;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;



	public class NetworkManager {
		private static NetworkManager instance;
		public static NetworkManager getInstnace() {
			if (instance == null) {
				instance = new NetworkManager();
			}
			return instance;
		}
		
		AsyncHttpClient client;
		private NetworkManager() {
			
			try {
				KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
				trustStore.load(null, null);
				MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
				socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				client = new AsyncHttpClient();
				client.setSSLSocketFactory(socketFactory);			
				client.setCookieStore(new PersistentCookieStore(MyApplication.getContext()));
				client.setTimeout(30000);
			} catch (KeyStoreException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			} catch (UnrecoverableKeyException e) {
				e.printStackTrace();
			}
		}
		
		public HttpClient getHttpClient() {
			return client.getHttpClient();
		}
		
		public interface OnResultListener<T> {
			public void onSuccess(T result);
			public void onFail(int code); 
		}
		
		//public static final String SERVER = "http://zzanghansol.mooo.com";
		//public static final String HTTPS_SERVER = "http://zzanghansol.mooo.com";
		public static final String SERVER = "http://yeolwoo.mooo.com";
		public static final String HTTPS_SERVER = "https://yeolwoo.mooo.com";
		
		
		/*-----------로그인---------*/			
		public static final String LOGIN_URL = HTTPS_SERVER + "/users/login";
		public void login(Context context, String user_id, String user_pass, String user_phone, String user_regid, final OnResultListener<LoginResult> listener) {
			RequestParams params = new RequestParams();
			params.put("user_id", user_id);
			params.put("user_pw", user_pass);
			params.put("user_phone", user_phone);
			params.put("user_regid", user_regid);
			Log.i("login", "login");
			
			client.post(context, LOGIN_URL, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) { 
					Gson gson = new Gson();
					LoginResult results = gson.fromJson(responseString, LoginResult.class);
					listener.onSuccess(results); 
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					listener.onFail(statusCode); 
				}
			});
		}
		
		
		/*----------중복로그인 시도---------*/
		public static final String ACCEPT_LOGIN_URL = HTTPS_SERVER + "/users/acceptlogin";
		public void acceptLogin(Context context, String user_id, String user_pass, String user_phone, String user_regid, final OnResultListener<LoginResult> listener) {
			RequestParams params = new RequestParams();
			params.put("user_id", user_id);
			params.put("user_pw", user_pass);
			params.put("user_phone", user_phone);
			params.put("user_regid", user_regid);
			
			client.post(context, ACCEPT_LOGIN_URL, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) { 
					Gson gson = new Gson();
					LoginResult results = gson.fromJson(responseString, LoginResult.class);
					listener.onSuccess(results); 
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					listener.onFail(statusCode); 
				}
			});
		}
		
		/*-----------회원가입---------*/	
		public static final String JOIN_URL = HTTPS_SERVER + "/users/join";
		public void addjoin(Context context, String user_id, String user_pw, String user_phone, String user_regid, String user_regdate, final OnResultListener<JoinResult> listener) {
			RequestParams param = new RequestParams();
			param.put("user_id", user_id);
			param.put("user_pw", user_pw);
			param.put("user_phone", user_phone);
			param.put("user_regid", user_regid);
			param.put("user_regdate", user_regdate);
			
			client.post(context, JOIN_URL, param, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					Gson gson = new Gson();
					JoinResult results = gson.fromJson(responseString, JoinResult.class);
					listener.onSuccess(results); 
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) { 
					listener.onFail(statusCode); 
				}
			});
		}
		
		/*-----------가입정보 조회---------*/
		//더미public static final String SEARCH_JOIN_INFO_URL = SERVER + "/users/join/%s/%s/%s";
		/*
		    public void searchJoinInfo(Context context, int join_code, String gender, int user_req, final OnResultListener<JoinResult> listener) {
			RequestParams param = new RequestParams();
			param.put("join_code", join_code);
			param.put("gender", gender);
			param.put("user_req", user_req);
			String url = String.format(SEARCH_JOIN_INFO_URL, join_code, gender, user_req);
			
			client.get(context, url, param, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) { 
					Gson gson = new Gson();
					JoinResult results = gson.fromJson(responseString, JoinResult.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) { 
					listener.onFail(statusCode); 
				}
			});
		}
		*/
		
		//실서버
		public static final String SEARCH_JOIN_INFO_URL = SERVER + "/users/join";
		public void searchJoinInfo(Context context, final OnResultListener<JoinResult> listener) {
			
			client.get(context, SEARCH_JOIN_INFO_URL, new TextHttpResponseHandler() {
		
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) { 
					Gson gson = new Gson();
					JoinResult results = gson.fromJson(responseString, JoinResult.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) { 
					listener.onFail(statusCode); 
				}
			});
		}
		
		
		/*----------커플요청---------*/
		public static final String COUPLE_ASK_URL = SERVER + "/couple/ask";
		public void coupleAsk(Context context, String auth_phone, String user_gender, final OnResultListener<JoinResult> listener) {
			RequestParams params = new RequestParams();
			params.put("auth_phone", auth_phone);
			params.put("user_gender", user_gender);
			
			client.post(context, COUPLE_ASK_URL, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) { 
					Gson gson = new Gson();
					JoinResult results = gson.fromJson(responseString, JoinResult.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					listener.onFail(statusCode); 
				}
			});
		}
		
		/*-----------커플응닫---------*/
		public static final String COUPLE_ANSWER_URL = SERVER + "/couple/answer";
		public void coupleAnswer(Context context, final OnResultListener<JoinResult> listener) {
			client.post(COUPLE_ANSWER_URL, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					Gson gson = new Gson();
					JoinResult results = gson.fromJson(responseString, JoinResult.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					listener.onFail(statusCode); 
				}
			});
		}
		
		/*-----------공통정보 입력(기념일, 생일)---------*/
		public static final String USERS_COMMON_URL = SERVER + "/users/common";
		public void addCommonInfo(Context context, String couple_birth, String user_birth, final OnResultListener<JoinResult> listener) {
			RequestParams params = new RequestParams();
			params.put("couple_birth", couple_birth);
			params.put("user_birth", user_birth);
			
			client.post(context, USERS_COMMON_URL, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					Gson gson = new Gson();
					JoinResult results = gson.fromJson(responseString, JoinResult.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					listener.onFail(statusCode); 
				}
			});
		}
	 
		/*-----------여성정보 입력(생리주기, 약 여부 등등)---------*/
		public static final String ADD_WOMAN_INFO_URL = SERVER + "/users/woman";
		public void addWomanInfo(Context context, WomanInfoParcelData data, final OnResultListener<JoinResult> listener) {
			RequestParams params = new RequestParams(); 
			params.put("womaninfodata", data);
			Gson gson = new Gson();
			String json = gson.toJson(data);
			try {
				client.post(context, ADD_WOMAN_INFO_URL, new StringEntity(json), "application/json", new TextHttpResponseHandler() {
					
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						Gson gson = new Gson();
						JoinResult results = gson.fromJson(responseString, JoinResult.class);
						listener.onSuccess(results);				
				}
					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						// TODO Auto-generated method stub
						listener.onFail(statusCode); 
					}
				});
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/*-----------커플정보---------*/
		public static final String COUPLE_INFO_URL = SERVER + "/couple";
		
		public void getCoupleInfoList(Context context, final OnResultListener<CoupleInfoResult> listener) {
			
			client.get(context, COUPLE_INFO_URL, new TextHttpResponseHandler() {

				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					Gson gson = new Gson();
					CoupleInfoResponse results = gson.fromJson(responseString, CoupleInfoResponse.class);
					CoupleInfoResult list = results.result;
					listener.onSuccess(list);
				}
			
			});
		}
		
		/*-----------사용자 정보---------*/
		public static final String USER_INFO_URL = SERVER + "/users/userinfo";
		public void getUserInfo(Context context, final OnResultListener<UserInfoResult> listener) {
			
			client.get(context, USER_INFO_URL, new TextHttpResponseHandler() {

				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) { 
					listener.onFail(statusCode); 
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) { 
					Gson gson = new Gson();
					UserInfoResponse results = gson.fromJson(responseString, UserInfoResponse.class);
					UserInfoResult list = results.result;
					listener.onSuccess(list);
				}
				
			});
		}
		
		/*--------내기분 변경--------*/
		public static final String MY_CONDITION_URL = SERVER + "/couple/mycondition";
		public void myCondition(Context context, int condition_no, final OnResultListener<UserInfoResult> listener) {
			
			RequestParams params = new RequestParams();
			params.put("condition_no", ""+condition_no);
			client.post(context, MY_CONDITION_URL, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					UserInfoResponse results = gson.fromJson(responseString, UserInfoResponse.class);
					UserInfoResult list = results.result;
					listener.onSuccess(list);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		 
		/*-----------DDAY---------*/
		public static final String DDAY_SEARCH_URL = SERVER + "/ddays";	
		public void getDdayList(Context context, final OnResultListener<DdayResult> listener) {
			RequestParams params = new RequestParams();
			
			client.get(context, DDAY_SEARCH_URL, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					Gson gson = new Gson();
					DdayResponse results = gson.fromJson(responseString, DdayResponse.class);
					DdayResult list = results.result;
					listener.onSuccess(list); 
				} 
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode); 
				}
			});
		}
		  
		public static final String DDAY_ADD_URL = SERVER + "/ddays/add";
		public void addDday(Context context, String ddayname, String ddaydate, int ddayrepeat, final OnResultListener<DdayResponse> listener) {
			RequestParams params = new RequestParams();
			params.put("dday_repeat", ""+ddayrepeat);
			params.put("dday_name", ddayname);
			params.put("dday_date", ddaydate);
			
			client.post(context, DDAY_ADD_URL, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) { 
					Gson gson = new Gson();
					DdayResponse results = gson.fromJson(responseString, DdayResponse.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) { 
					listener.onFail(statusCode);  
				}
			});
		}
		 
		public static final String DDAY_EDIT_URL = SERVER + "/ddays/%s/modify";
		public void editDday(Context context, int ddayno, String ddayname, String ddaydate, int ddayrepeat, final OnResultListener<DdayResponse> listener) {
			RequestParams params = new RequestParams(); 
			params.put("dday_name", ddayname);
			params.put("dday_date", ddaydate);
			
			String url = String.format(DDAY_EDIT_URL, ddayno);
			
			client.post(context, url, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) { 
					Gson gson = new Gson();
					DdayResponse results = gson.fromJson(responseString, DdayResponse.class);
					listener.onSuccess(results); 
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					listener.onFail(statusCode);
					
				}
			}); 
		}
		
		public static final String DDAY_DELETE_URL = SERVER + "/ddays/%s/delete";
		public void deleteDday(Context context, int ddayno, final OnResultListener<DdayResponse> listener) {
			String url = String.format(DDAY_DELETE_URL, ddayno);
			
			client.post(context, url, null, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					DdayResponse results = gson.fromJson(responseString, DdayResponse.class);
					listener.onSuccess(results); 
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode);
				}
			});
		}
		
		/*-----------여성정보조회---------*/
		public static final String GET_WOMAN_INFO_URL = SERVER + "/setting/herself/%s";
		public void getWomanInfoList(Context context, String user_gender, final OnResultListener<WomanInfoResponse> listener){
			 
			String url = String.format(GET_WOMAN_INFO_URL, user_gender);
			 client.get(context, url, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					WomanInfoResponse results = gson.fromJson(responseString, WomanInfoResponse.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode);
				}
			});
		}  
		
		
		/*-----------LOVE---------*/
		public static final String LOVE_SEARCH_URL = SERVER + "/loves/%s/%s/%s";
		public void getLoveList(Context context, int orderby, int year, int month, final OnResultListener<LoveSearchResult> listener) {
		/*RequestParams params = new RequestParams();
		params.put("year", ""+year);
		params.put("month", ""+month);
		params.put("orderby", ""+orderby);*/
		String url = String.format(LOVE_SEARCH_URL, ""+year, ""+month, ""+orderby);
		
		client.get(context, url, null ,new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) { 
				Gson gson = new Gson();
				LoveSearchResult results = gson.fromJson(responseString, LoveSearchResult.class); 
				listener.onSuccess(results);  
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) { 
				listener.onFail(statusCode);
			}
		});
	}
	
		public static final String LOVE_ADD_URL = SERVER + "/loves/add";
		public void addLove(Context context, int iscondom, String loves_date, final OnResultListener<LoveSearchResult> listener) {
			RequestParams params = new RequestParams();
			params.put("loves_condom", ""+iscondom);
			params.put("loves_date", loves_date);
			
			client.post(context, LOVE_ADD_URL, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					LoveSearchResult results = gson.fromJson(responseString, LoveSearchResult.class);
					listener.onSuccess(results); 
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode);
				}
			});
		} 
		
		public static final String LOVE_EDIT_URL = SERVER + "/loves/%s/modify";	
		public void modifyLove(Context context, int loves_no, int is_condom, String date, final OnResultListener<LoveSearchResult> listener) {
			RequestParams params = new RequestParams();
			//params.put("loves_no", ""+relation_no);
			params.put("loves_condom", ""+is_condom);
			params.put("loves_date", date);
			String url = String.format(LOVE_EDIT_URL, ""+loves_no);
			
			client.post(context, url, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					LoveSearchResult results = gson.fromJson(responseString, LoveSearchResult.class);
					listener.onSuccess(results);  
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode);
				}
			}); 
		}
		
		public static final String LOVE_DELETE_URL = SERVER + "/loves/%s/delete";
		public void deleteLove(Context context, int loves_no, final OnResultListener<LoveSearchResult> listener) {
			//RequestParams params = new RequestParams();
			//params.put("loves_no", ""+relation_no);
			String url = String.format(LOVE_DELETE_URL, ""+loves_no);
			
			client.post(context, url, null, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					LoveSearchResult results = gson.fromJson(responseString, LoveSearchResult.class);
					listener.onSuccess(results);  
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode);
				}
			});
		}
		
		
		/*-----------미션---------*/
		public static final String MISSION_ING_LIST=SERVER+"/missions";
		public void getMissionIngList(Context context,  final OnResultListener<MissionIngResult> listener){
			String url = String.format(MISSION_ING_LIST);
			client.get(context, url, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					MissionIngResult results = gson.fromJson(responseString, MissionIngResult.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode); 
				}
			});
		}
		public static final String MISSION_LIST_URL = SERVER +  "/missions/%s/%s/%s";
		public void getMissionList(Context context, int year, int month, int orderby, final OnResultListener<MissionResult> listener) {
			
			
			String url = String.format(MISSION_LIST_URL, year, month, orderby);
			client.get(context, url, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					MissionResult results = gson.fromJson(responseString, MissionResult.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode); 
				}
			});
		}
		
		public static final String MISSION_ADD_URL = SERVER + "/missions/add";
		public void addMission(Context context, int theme_no, final OnResultListener<MissionResult> listener) {
			RequestParams params = new RequestParams();
			params.put("theme_no",theme_no); 
			
			client.post(context, MISSION_ADD_URL, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					MissionResult results = gson.fromJson(responseString, MissionResult.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode); 
				}
			});
			
		}
		
		public static final String MISSION_CONFIRM_URL = SERVER + "/missions/%s/confirm";
		public void confirmMission(Context context, int mlist_no, final OnResultListener<MissionResult> listener) {
			RequestParams params = new RequestParams();
			params.put("mlist_no", mlist_no); 
			String url = String.format(MISSION_CONFIRM_URL, mlist_no);
			client.post(context, url, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					MissionResult results = gson.fromJson(responseString, MissionResult.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode); 
				}
			});
			
		}
		
		
		
		public static final String MISSION_INFO_URL = SERVER + "/missions/%s";
		public void searchMissionInfo(Context context, int mlist_no, final OnResultListener<MissionResult> listener) {
			RequestParams param = new RequestParams();
			param.put("mlist_no", mlist_no); 
			String url = String.format(MISSION_INFO_URL, mlist_no);
			
			client.get(context, url, param, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) { 
					Gson gson = new Gson();
					MissionResult results = gson.fromJson(responseString, MissionResult.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) { 
					listener.onFail(statusCode); 
				}
			});
		}
		
		public static final String SUCCESS_MISSION_URL = SERVER + "/missons/%s/success";
		public void successMisson(Context context, int mlist_no, final OnResultListener<MissionResult> listener) {
			RequestParams params = new RequestParams();
			params.put("mlist_no", mlist_no); 
			String url = String.format(SUCCESS_MISSION_URL, mlist_no);
			
			client.post(context, url, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					MissionResult results = gson.fromJson(responseString, MissionResult.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode); 
				}
			}); 
		}
		public static final String ASKPOPUP_MISSION_URL = SERVER + "/missions/askpopup";
		public void askpopup(Context context,int mlist_no,final OnResultListener<MissionResult> listener){
			RequestParams params = new RequestParams();
			params.put("mlist_no", mlist_no); 
			
			client.post(context, ASKPOPUP_MISSION_URL, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					MissionResult results = gson.fromJson(responseString, MissionResult.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode); 
				}
			});
		}
		
		//--------아이템---------
		
		public static final String POSSIBLE_ITEM_LIST_URL= SERVER + "/items";
		public void getBuyInfo(Context context, final OnResultListener<BananaItemResponse> listener) {
			client.get(context, POSSIBLE_ITEM_LIST_URL, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					Gson gson = new Gson();
					BananaItemResponse results = gson.fromJson(responseString, BananaItemResponse.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode);
				}
			});
		}
		
		
		public static final String OWN_ITEM_LIST_URL= SERVER + "/items/own";
		public void getOwnItemInfo(Context context, final OnResultListener<BananaItemResponse> listener) {
			client.get(context, OWN_ITEM_LIST_URL, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					BananaItemResponse results = gson.fromJson(responseString, BananaItemResponse.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode);
				}
			});
		}
		
		
		public static final String USE_ITEM_URL = SERVER + "/items/%s/use/%s";
		public void useItem(Context context, int item_no, int mlist_no,String mission_name, final OnResultListener<BananaItemResponse> listener) {
			RequestParams params = new RequestParams();
			params.put("item_no", item_no); 
			params.put("mlist_no", mlist_no);
			params.put("mission_name",mission_name);
			String url = String.format(USE_ITEM_URL, item_no, mlist_no);
			
			client.post(context, url, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					Gson gson = new Gson();
					BananaItemResponse results = gson.fromJson(responseString, BananaItemResponse.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					listener.onFail(statusCode); 
				}
			}); 
		}
		
		/*-----------설정창---------*/
		public static final String NOTICE_URL = SERVER + "/setting/notice";
		public void getNotic(Context context, final OnResultListener<NoticeResponse> listener) {
			
			client.get(context, NOTICE_URL, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					Gson gson = new Gson();
					NoticeResponse results = gson.fromJson(responseString, NoticeResponse.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					listener.onFail(statusCode);
				}
			});
		}
	
		public static final String LOGOUT_URL = SERVER + "/users/logout";
		public void logout(Context context, final OnResultListener<LogoutResponse> listener) {
			
			client.post(LOGOUT_URL, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					Gson gson = new Gson();
					LogoutResponse results = gson.fromJson(responseString, LogoutResponse.class);
					listener.onSuccess(results);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					listener.onFail(statusCode);
				}
			});
		}
		
		public static final String WITHDRAW_URL = SERVER + "/users/withdraw";
		public void withDraw(Context context, final OnResultListener<WithDrawReponse> listener) {
			 
			client.post(WITHDRAW_URL, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) { 
					Gson gson = new Gson();
					WithDrawReponse results = gson.fromJson(responseString, WithDrawReponse.class);
					listener.onSuccess(results);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) { 
					listener.onFail(statusCode);
				}
			});
		}
		
		
	}
