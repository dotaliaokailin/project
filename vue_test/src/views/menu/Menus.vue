<template>
    <div class="menu-container">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px; padding-bottom: 10px; font-size: 12px">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>菜单权限</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card class="users-box-card">
        <el-input
          :style="{width: '30%',marginRight: '10px'}"
          clearable
          placeholder="输入关键字进行过滤"
          v-model="filterText">
        </el-input>
        <el-button type="primary" icon="el-icon-plus" @click="show(0, true)">父级</el-button>
        <el-button type="info" icon="el-icon-download">导出</el-button>
        <p>菜单权限树</p>
        <el-tree
          :data="data"
          :props="defaultProps"
          accordion
          show-checkbox
          :expand-on-click-node="false"
          :filter-node-method="filterNode"
          ref="tree">
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span class="left_span">
              <span>
                <i :class="data.icon"></i>
              </span>
              <span style="margin: 0px 10px; font-size: 14px">{{ node.label }}</span>
              <span style="color: #b5b5b5; font-size: 12px">
                <el-tag :type="data.type ==0 ? 'success' : 'warning'" size="mini" effect="plain">
                  {{data.type == 0 ? '菜单' : '权限['+data.perms+']'}}
                </el-tag>
              </span>
            </span>
            <span class="right_span">
              <button type="button" class="el-button el-button--text el-button--mini" @click="show(data.id, false)">
                <span>
                  <i class="el-icon-edit"></i>编辑
                </span>
              </button>
              <button type="button" class="el-button el-button--text el-button--mini" @click="show(data.id, true)">
                <span>
                  <i class="el-icon-plus"></i>添加
                </span>
              </button>
              <button type="button" class="el-button el-button--text el-button--mini">
                <span>
                  <i class="el-icon-delete"></i>删除
                </span>
              </button>
            </span>
          </span>
        </el-tree>
      </el-card>
      <!-- 新增编辑弹框子组件 -->
      <add-menu :addOrUpdateVisible="addOrUpdateVisible" @changeShow="showAddOrUpdate"  :id="id" :flag="flag"></add-menu>
    </div>
</template>

<script>
    import {menuButtonTree} from "../../api/menuApi";
    import AddMenu from  "../menu/AddMenu";
    export default {
      name: "Menus",
      components: {
        AddMenu
      },
      watch: {
        filterText(val) {
          this.$refs.tree.filter(val);
        }
      },
      methods: {
        // 按钮点击事件 显示新增编辑弹窗组件
        show(id, flag){
          this.id = id;
          this.flag = flag;
          this.addOrUpdateVisible = true;
        },
        // 监听 子组件弹窗关闭后触发，有子组件调用
        showAddOrUpdate(data){
          if(data === 'false'){
            this.addOrUpdateVisible = false;
          }else{
            this.addOrUpdateVisible = true;
          }
        },
        filterNode(value, data) {
          if (!value) return true;
          return data.menuName.indexOf(value) !== -1;
        },
        async menuButtonTree(){
          const {data} = await menuButtonTree();
          if(data.status){
            this.data = data.data.menuButtonTree;
          }else {
            this.$message.error(data.message);
          }
        }
      },
      created(){
        this.menuButtonTree();
      },
      data() {
        return {
          filterText: '',
          data: [],
          id: 0,
          flag: true,
          addOrUpdateVisible: false,
          defaultProps: {
            children: 'children',
            label: 'menuName',
          }
        };
      }
    }
</script>

<style scoped lang="less">
  .menu-container{
    margin: 0;
    padding: 0;
    height: 100%;
    width: 100%;
    .custom-tree-node{
      box-sizing: border-box;
      width: 100%;
      .left_span{
        justify-content: left;
      }
      .right_span{
        float: right;
        justify-content:flex-end;
      }
    }
  }

</style>
