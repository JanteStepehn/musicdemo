export const mixin = {
  methods:{
        //提示信息
    notify(title,type){
        this.$notify({
            title: title,
            type: type
        })
    },
    //根据相对地址获取绝对地址
    getUrl(url){
      var uri = this.$store.state.HOST;
      var uri2 = url;
      return uri+'/'+uri2
    },
    //获取性别中文
    changeSex(value){
      if(value == 0){
        return '女';
      }
      if(value == 1){
        return '男';
      }
      if(value == 2){
        return '组合';
      }
      return value;
    },
    //获取生日
    attachBirth(val){
        return String(val).substr(0,9);
    },
    //上传图片之前的校验
    beforeAvatorUpload(file){
        const isJPG = (file.type === 'image/jpg')||(file.type === 'image/png');
        if(!isJPG){
          this.$message.error('上传图片只能是jpg或png');
          return false;
        }
        const isLt2M = (file.size / 1024 /1024) > 2;
        if(isLt2M){
          this.$message.error('上传图片不能超过2MB');
          return false;
        }
        return true;
     },
     //上传成功
     handleAvatorSuccess(response){
        let _this = this;
        if(response.code == 1){
          _this.getData();
          _this.$notify({
            title: '上传成功',
            type: 'success'
          });
        }else{
          _this.$notify({
            title: '上传失败',
            type: 'error'
          });
        }
     },
     //弹出删除窗口
     handleDelete(id){
        this.idx = id;
        this.delVisible = true;
     },
     //把已选择的项赋值给multipleSelection
     handleSelectionChange(val){
        this.multipleSelection = val;
     },
     //批量删除
     delAll(){
       for(let item of this.multipleSelection){
         this.handleDelete(item.id);
         this.deleteRow();
       }
       this.multipleSelection = [];
     }
  }
}