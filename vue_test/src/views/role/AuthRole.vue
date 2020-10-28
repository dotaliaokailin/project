<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" title="分配菜单权限" :visible.sync="showDialog" v-if="showDialog">
      <el-tree
        ref="tree"
        :props="defaultProps"
        :data="menus"
        show-checkbox
        node-key="id"
        :default-checked-keys="menuIds">
      </el-tree>
      <div slot="footer">
        <el-button @click="onClose">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {getMenuByRoleId, save} from "../../api/roleApi";

export default {
  name: 'AuthRole',
  inheritAttrs: false,
  components: {},
  props: {
    authVisible:{
      type: Boolean,
      default: false
    },
    id:{
      type: Number,
      default: -1
    }
  },
  data() {
    return {
      // 控制弹出框显示隐藏
      showDialog:false,
      formData: {},
      rules: {},
      menus:[],
      menuIds:[],
      defaultProps: {
        children: 'children',
        label: 'menuName',
        value: 'id',
        disabled: "disabled",
      }
    }
  },
  computed: {},
  watch: {
    // 监听 addOrUpdateVisible 改变
    authVisible(oldVal,newVal){
      this.showDialog = this.authVisible;
      if(this.showDialog){
        this.getMenus();
      }
    },
  },
  created() {},
  mounted() {},
  methods: {
    onOpen() {
    },
    onClose() {
      // 子组件调用父组件方法，并传递参数
      this.$emit('changeShow','false');
    },
    async submitForm() {
      const {data} = await save(this.$refs.tree.getCheckedKeys(), this.id);
      if(data.status){
        this.$message.success(data.message);
        this.$emit('update:visible', false);
        // 子组件调用父组件方法，并传递参数
        this.$emit('changeShow','false');
      }else {
        this.$message.error(data.message);
      }
    },
    async getMenus(){
      const {data} = await getMenuByRoleId(this.id);
      if(data.status){
        this.menus = data.data.menus;
        this.menuIds = data.data.menuIds;
      }else {
        this.$message.error(data.message);
      }
    }
  }
}

</script>
<style>
</style>
