import Axios from "axios";
import {get,post} from "./http"

//=======歌手相关===========
//查询歌手
export const getAllSinger = () => post('singer/allSinger');

//根据性别查询
export const getSingerBySex = (sex) => get('singer/selectBySex/'+sex);


//==========g歌曲相关===============
//根据歌手id查询歌曲
export const selectBySingerId = (id) => get('song/singer/detail/'+id);

//根据歌曲id查询歌曲
export const selectBySongId = (id) => get('song/detail/'+id);

//根据歌曲名查询
export const selectByName = (keywords) => get('song/detailByLikeName/'+keywords);


//===========歌单相关==========
//查询歌单
export const getAllSongList = () => post('songList/allSongList');

//根据歌单名查询歌单
export const selectByTitle = (keywords) => get('songList/selectByTitle/'+keywords);

//根据风格查询歌单
export const selectByStyle = (style) => get('songList/selectByStyle/'+style);


//=========歌单 中歌曲相关========
//根据歌单id查询歌曲
export const listSongDetail = (id) => get('listSong/detail/'+id);

//==================用户相关============
//查询用户
export const getAllConsumer = () => post('consumer/allConsumer');

//注册
export const SignUp = (params) => post('/consumer/add',params);
//登录
export const loginIn = (params) => post('/consumer/login',params);

//根据用户id查询
export const getUserById = (id) => get('/consumer/selectById/'+id);

//更新用户信息
export const updateUserMsg = (params) => post('/consumer/update',params);


//下载音乐
export const download = (url) => Axios({
    method: 'get',
    url: url,
    responseType: 'blob'
});


//==========评价=================
//新增
export const setRank = (params) => post('/rank/add',params);

//平均分
export const getAvg = (songListId) => get('/rank/avg/'+songListId);


//===========评论===================
//提交评论
export const setComment = (params) => post('/comment/add',params);

//点赞
export const setLike = (params) => post('/comment/like',params);

//返回当前歌单或歌曲的评论列表
export const getAllComment = (type,id) => {
    if(type == 0){
        return get('/comment/selectBySongId/'+id);
    }else{
        return get('/comment/selectBySongListId/'+id);
    }
}


//=========收藏=============
//新增收藏
export const setCollect = (params) => post('/collect/add',params);

//查询用户的收藏
export const getCollect = (userId) => get('/collect/selectBySongId/'+userId);

//是否已收藏
export const existCollect = (params) => post('/collect/existCollect',params);