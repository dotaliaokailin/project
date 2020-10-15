<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" :title="userRoleName" :visible.sync="showDialog" v-if="showDialog" center>
      <span>
        <el-transfer
          filterable
          filter-placeholder="请输入角色名称"
          v-model="haveRoles"
          :titles="['全部角色','拥有角色']"
          :data="roles">
       </el-transfer>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="onClose">取消分配</el-button>
        <el-button type="primary" @click="handelConfirm">确定分配</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
    import {userRoles} from '../../api/userApi';
    import {addUserRoles} from '../../api/userApi';
    export default {
      name: "UserRole",
      props: {
        userRoleVisible:{
          type: Boolean,
          default: false
        },
        userRoleId: {
          type: Number,
          default: 0
        },
        userRoleName: {
          type: String,
          default: ''
        }
      },
      data() {
        return {
          roles: [],
          haveRoles: [],
          // 控制弹出框显示隐藏
          showDialog:false,
        };
      },
      watch:{
        // 监听 addOrUpdateVisible 改变
        userRoleVisible(oldVal,newVal){
          this.showDialog = this.userRoleVisible;
          if(this.userRoleVisible){
            this.roles = [],
            this.haveRoles = [],
            this.userRoles(this.userRoleId);
          }
        }
      },
      created() {

      },
      methods : {
        onOpen() {},
        onClose() {
          // 子组件调用父组件方法，并传递参数
          this.$emit('changeShow','false');
        },
        async handelConfirm() {
          console.log(this.haveRoles);
          const {data} = await addUserRoles(this.haveRoles, this.userRoleId);
          if(data.status){
            this.$emit('update:visible', false);
            // 子组件调用父组件方法，并传递参数
            this.$emit('changeShow','false');
            this.Message.success(data.message);
          }else{
            this.Message.error(data.message);
          }
        },
        async userRoles(id) {
          const {data} = await userRoles(id);
          this.haveRoles = data.data.userRoleIds;
          if(data.data.roles.length > 0){
            data.data.roles.forEach((roles) => {
              this.roles.push({
                label: roles.roleName,
                key: roles.id,
              });
            });
          }
        }
      }
    }
</script>

<style scoped lang="less">
  .el-transfer {
    padding-left: 165px;
  }
</style>
