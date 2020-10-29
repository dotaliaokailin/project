<template>
  <div>
    <el-form ref="elForm" :model="deptList" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="部门名称" prop="name">
        <el-input v-model="deptList.name" placeholder="请输入部门名称" :maxlength="20" clearable
          :style="{width: '100%'}"></el-input>
      </el-form-item>
      <el-form-item label="部门电话" prop="phone">
        <el-input v-model="deptList.phone" placeholder="请输入部门电话" :maxlength="11" clearable
          :style="{width: '100%'}"></el-input>
      </el-form-item>
      <el-form-item label="部门地址" prop="address">
        <el-input v-model="deptList.address" type="textarea" placeholder="请输入部门地址" :maxlength="255"
          :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
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
        name: undefined,
        phone: undefined,
        address: undefined,
      },
      rules: {
        name: [],
        phone: [{
          required: true,
          message: '请输入部门电话',
          trigger: 'blur'
        }],
        address: [{
          required: true,
          message: '请输入部门地址',
          trigger: 'blur'
        }],
      },
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
