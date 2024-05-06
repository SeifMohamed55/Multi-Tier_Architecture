export const BASE_URL = 'http://localhost:8080';

export const FOODS_URL = 'http://localhost:8081' + '/api/food';
export const FOODS_TAGS_URL = FOODS_URL + '/tags';
export const FOODS_BY_SEARCH_URL = FOODS_URL + '/search/';
export const FOODS_BY_TAG_URL = FOODS_URL + '/tag/';
export const FOODS_BY_ID_URL = FOODS_URL + '/id/';

export const USER_LOGIN_URL = BASE_URL + '/login';
export const USER_LOGOUT_URL = BASE_URL + '/logout';
export const USER_REGISTER_URL = BASE_URL + '/register';
export const USER_UPDATE_URL = BASE_URL + '/client/updateUser';
export const USER_CHANGE_PASSWORD_URL = BASE_URL + '/client/changePassword';

export const ADMIN_REGISTE_URL = BASE_URL + '/admin/registration';
export const ALL_ADMINS_URL = BASE_URL + '/admin/allAdmins'
export const ALL_CLIENTS_URL = BASE_URL + '/admin/allClients'
export const DELETE_CLIENT = BASE_URL + '/admin/delete/'
export const UPDATE_FOOD_URL = BASE_URL + '/admin/updateFood'
export const ALL_FOOD = BASE_URL + '/admin/allFood'
export const HIDE_FOOD = BASE_URL + '/admin/hideFood'
export const UNHIDE_FOOD = BASE_URL + '/admin/unhideFood'
export const ADD_FOOD = BASE_URL + '/admin/addFood'


export const ORDERS_URL = BASE_URL + '/order';
export const ORDER_CREATE_URL = ORDERS_URL + '/create';
export const ORDER_NEW_FOR_CURRENT_USER_URL = ORDERS_URL + '/newOrderForCurrentUser';
export const ALL_ORDERS_FOR_USER = ORDERS_URL + '/all'

export const ORDER_PAY_URL = ORDERS_URL + '/pay';
export const ORDER_TRACK_URL = ORDERS_URL + '/track/';
export const USER_KEY = 'User'
