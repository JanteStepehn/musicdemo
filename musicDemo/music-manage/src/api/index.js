import {get,post} from "./http"

//判断 admin 是否登录成功
export const getLoginStatus = (params) => post('admin/login/status',params);


//=======歌手相关===========
//查询歌手
export const getAllSinger = () => post('singer/allSinger');

//添加歌手
export const setSinger = (params) => post('singer/add',params);

//编辑歌手
export const updateSinger = (params) => post('singer/update',params);

//删除歌手
export const delSinger = (params) => post('singer/delete',params);

//=====================g歌曲相关===============
//根据歌手id查询歌曲
export const selectBySingerId = (id) => get('song/singer/detail/'+id);

//编辑歌曲
export const updateSong = (params) => post('song/update',params);

//删除歌曲
export const delSong = (id) => post('song/delete/'+id);

//根据歌曲id查询歌曲
export const selectBySongId = (id) => get('song/detail/'+id);

//根据歌曲名查询
export const selectByName = (name) => get('song/detailByName/'+name);

//查询所有歌曲
export const getAllSong = () => post('song/allSong');

//===========歌单相关==========
//查询歌单
export const getAllSongList = () => post('songList/allSongList');

//添加歌单
export const setSongList = (params) => post('songList/add',params);

//编辑歌单
export const updateSongList = (params) => post('songList/update',params);

//删除歌单
export const delSongList = (params) => post('songList/delete',params);


//=========歌单 中歌曲相关========
//根据歌单id查询歌曲
export const listSongDetail = (id) => get('listSong/detail/'+id);

//添加歌曲
export const addListSong = (params) => post('listSong/add',params);

//删除歌曲
export const delListSong = (params) => post('listSong/delete',params);


//==================用户相关============
//查询用户
export const getAllConsumer = () => post('consumer/allConsumer');

//添加用户
export const setConsumer = (params) => post('consumer/add',params);

//编辑用户
export const updateConsumer = (params) => post('consumer/update',params);

//删除用户
export const delConsumer = (params) => post('consumer/delete',params);

//根据用户id查询
export const getUserById = (id) => get('/consumer/selectById/'+id);

//=========收藏=============
//删除
export const delCollect = (userId,songId) => get('/collect/deleteByTwoId?userId='+userId+'&songId='+songId);

//查询用户的收藏
export const getCollect = (userId) => get('/collect/selectBySongId/'+userId);

//是否已收藏
export const existCollect = (params) => post('/collect/existCollect',params);

//=========评论=============
//删除
export const delComment = (id) => get('/comment/delete?id='+id);

//查询歌单评论列表
export const getComment = (songListId) => get('/comment/selectBySongListId/'+songListId);

