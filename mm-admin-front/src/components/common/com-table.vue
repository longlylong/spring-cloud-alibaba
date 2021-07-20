<template>
  <div class="table-management">
    <el-table
      ref="comtable"
      border
      style="width: 100%"
      row-key="id"
      :data="
        isLocalPage()
          ? data.slice(
              (page.curPage - 1) * page.pageSize,
              page.curPage * page.pageSize
            )
          : data
      "
      @selection-change="handleSelectionChange"
      v-loading="loading"
    >
      <el-table-column
        v-if="showSelect"
        align="center"
        type="selection"
        width="55"
        :reserve-selection="true"
      ></el-table-column>
      <slot></slot>
    </el-table>
    <div class="pagination">
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="page.curPage"
        :page-size="page.pageSize"
        :total="isLocalPage() ? data.length : page.totalCount"
        background
        layout="total,prev,pager,next"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    data: {
      type: Array,
      default: () => [],
    },
    selected: { type: Array },
    showSelect: { type: Boolean, default: false },
    loading: { type: Boolean },
    onPageChange: { type: Function },
    onSelectionChange: { type: Function },
    page: {
      type: Object,
      default: () => ({
        curPage: 1,
        pageSize: 10,
        totalCount: 0,
        totalPage: 0,
        localPage: true,
      }),
    },
  },
  data: () => ({
    curPage: 1,
    tableSelectedData: [],
  }),
  watch: {
    selected(arr) {
      this.$refs.comtable.clearSelection();
      if (!this.selected || this.selected.length == 0) {
        return;
      }
      this.selected.forEach((obj) => {
        this.$refs.comtable.toggleRowSelection(obj, true);
      });
    },
  },
  methods: {
    isLocalPage() {
      return (
        this.page.isLocalPage ||
        !this.page.totalCount ||
        this.page.totalCount == 0
      );
    },
    getSelectedData() {
      return this.tableSelectedData;
    },

    handleSelectionChange(arr) {
      this.tableSelectedData = arr;
      if (this.onSelectionChange) {
        this.onSelectionChange();
      }
    },
    handleCurrentChange(page) {
      this.curPage = page;
      if (this.page.localPage) {
        return;
      }
      this.onPageChange(page);
    },
  },
};
</script>


<style rel="stylesheet/scss" lang="scss" scoped>
.table-management {
  .pagination {
    float: right;
    margin: 20px;
  }

  .imgBox {
    width: 50px;
    height: 50px;
    margin: 0 auto;
    img {
      width: 100%;
      height: 100%;
    }
  }
}
</style>