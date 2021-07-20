<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    width="30%"
    :before-close="handleClose"
  >
    <span>{{ content }}</span>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">Cancel</el-button>
      <el-button type="primary" @click="confirm">Confirm</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: "ConfirmDialog",
  data: () => ({
    dialogVisible: false,
    title: "",
    content: "",
    params: {},
  }),
  watch: {
    title(newVal) {
      this.$emit("update", newVal);
    },
    content(newVal) {
      this.$emit("update", newVal);
    },
  },
  methods: {
    handleClose() {
      this.dialogVisible = false;
    },
    showDialog(data) {
      this.dialogVisible = true;
      this.title = data.title;
      this.content = data.content;
      this.params = data.params;
    },
    confirm() {
      this.handleClose();
      this.#emit("confirmCallback", this.params);
    },
  },
};
</script>
