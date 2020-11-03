<template>
  <div>
    <el-form ref="elForm" :model="deptList" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="节点名称" prop="parentName">
        <el-input v-model="deptList.parentName" placeholder="请输入节点名称" clearable :style="{width: '100%'}">
        </el-input>
      </el-form-item>
      <el-form-item label="URL" prop="url">
        <el-input v-model="deptList.url" placeholder="请输入URL" clearable :style="{width: '100%'}"></el-input>
      </el-form-item>
      <el-form-item label="权限编码" prop="perms">
        <el-input v-model="deptList.perms" placeholder="请输入权限编码" clearable :style="{width: '100%'}">
        </el-input>
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <el-input v-model="deptList.icon" placeholder="请输入图标" clearable :style="{width: '100%'}"></el-input>
      </el-form-item>
      <el-form-item label="是否可用" prop="available">
        <el-radio-group v-model="deptList.available" size="medium">
          <el-radio v-for="(item, index) in availableOptions" :key="index" :label="item.value"
            :disabled="item.disabled">{{item.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否展开" prop="open">
        <el-radio-group v-model="deptList.open" size="medium">
          <el-radio v-for="(item, index) in openOptions" :key="index" :label="item.value"
            :disabled="item.disabled">{{item.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-radio-group v-model="deptList.type" size="medium">
          <el-radio v-for="(item, index) in typeOptions" :key="index" :label="item.value"
            :disabled="item.disabled">{{item.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="排序" prop="orderNum">
        <el-input-number v-model="deptList.orderNum" placeholder="排序" :step='1' :min='1' :max='10'>
        </el-input-number>
      </el-form-item>
      <el-form-item size="large">
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  components: {},
  props: [],
  data() {
    return {
      deptList: {
        parentName: undefined,
        url: undefined,
        perms: undefined,
        icon: undefined,
        available: "",
        open: undefined,
        type: undefined,
        orderNum: undefined,
      },
      rules: {
        parentName: [{
          required: true,
          message: '请输入节点名称',
          trigger: 'blur'
        }],
        url: [],
        perms: [],
        icon: [],
        available: [{
          required: true,
          message: '是否可用不能为空',
          trigger: 'change'
        }],
        open: [{
          required: true,
          message: '是否展开不能为空',
          trigger: 'change'
        }],
        type: [{
          required: true,
          message: '类型不能为空',
          trigger: 'change'
        }],
        orderNum: [{
          required: true,
          message: '排序',
          trigger: 'blur'
        }],
      },
      availableOptions: [{
        "label": "不可用",
        "value": 0
      }, {
        "label": "可用",
        "value": 1
      }],
      openOptions: [{
        "label": "不展开",
        "value": 0
      }, {
        "label": "展开",
        "value": 1
      }],
      typeOptions: [{
        "label": "菜单",
        "value": 0
      }, {
        "label": "按钮",
        "value": 1
      }],
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}

</script>
<style>
</style>
