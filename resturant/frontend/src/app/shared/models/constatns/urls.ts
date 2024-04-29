export const BASE_URL = 'http://localhost:8080';

export const FOODS_URL = BASE_URL + '/api/food';
export const FOODS_TAGS_URL = FOODS_URL + '/tags';
export const FOODS_BY_SEARCH_URL = FOODS_URL + '/search/';
export const FOODS_BY_TAG_URL = FOODS_URL + '/tag/';
export const FOODS_BY_ID_URL = FOODS_URL + '/id/';

export const USER_LOGIN_URL = BASE_URL + '/login';
export const USER_LOGOUT_URL = BASE_URL + '/logout';
export const USER_REGISTER_URL = BASE_URL + '/register';
export const USER_UPDATE_URL = BASE_URL + '/update';
export const USER_CHANGE_PASSWORD_URL = BASE_URL + '/change-password';



export const ORDERS_URL = BASE_URL + '/order';
export const ORDER_CREATE_URL = ORDERS_URL + '/create';
export const ORDER_NEW_FOR_CURRENT_USER_URL = ORDERS_URL + '/newOrderForCurrentUser';

export const ORDER_PAY_URL = ORDERS_URL + '/pay';
export const ORDER_TRACK_URL = ORDERS_URL + '/track/';
export const USER_KEY = 'User'
