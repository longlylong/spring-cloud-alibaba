<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" label-width="68px" ref="queryForm">
      <el-form-item label="表名称" prop="tableName">
        <el-input
          @keyup.enter.native="handleQuery"
          clearable
          placeholder="请输入表名称"
          size="small"
          v-model="queryParams.tableName"
        />
      </el-form-item>
      <el-form-item label="表描述" prop="tableComment">
        <el-input
          @keyup.enter.native="handleQuery"
          clearable
          placeholder="请输入表描述"
          size="small"
          v-model="queryParams.tableComment"
        />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          end-placeholder="结束日期"
          range-separator="-"
          size="small"
          start-placeholder="开始日期"
          style="width: 240px"
          type="daterange"
          v-model="dateRange"
          value-format="yyyy-MM-dd"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery" icon="el-icon-search" size="mini" type="primary">搜索</el-button>
        <el-button @click="resetQuery" icon="el-icon-refresh" size="mini">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          @click="handleGenTable"
          icon="el-icon-download"
          size="mini"
          type="primary"
          v-hasPermi="['tool:gen:code']"
        >生成
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          @click="openImportTable"
          icon="el-icon-upload"
          size="mini"
          type="info"
          v-hasPermi="['tool:gen:import']"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          :disabled="single"
          @click="handleEditTable"
          icon="el-icon-edit"
          size="mini"
          type="success"
          v-hasPermi="['tool:gen:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          :disabled="multiple"
          @click="handleDelete"
          icon="el-icon-delete"
          size="mini"
          type="danger"
          v-hasPermi="['tool:gen:remove']"
        >删除
        </el-button>
      </el-col>
    </el-row>

    <el-table :data="tableList" @selection-change="handleSelectionChange" v-loading="loading">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column align="center" label="序号" type="index" width="50">
        <template slot-scope="scope">
          <span>{{(queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column
        :show-overflow-tooltip="true"
        align="center"
        label="表名称"
        prop="tableName"
        width="130"
      />
      <el-table-column
        :show-overflow-tooltip="true"
        align="center"
        label="表描述"
        prop="tableComment"
        width="130"
      />
      <el-table-column
        :show-overflow-tooltip="true"
        align="center"
        label="实体"
        prop="className"
        width="130"
      />
      <el-table-column align="center" label="创建时间" prop="createTime" width="160"/>
      <el-table-column align="center" label="更新时间" prop="updateTime" width="160"/>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-button
            @click="handlePreview(scope.row,1)"
            icon="el-icon-view"
            size="small"
            type="text"
            v-hasPermi="['tool:gen:preview']">
            预览MyBatis
          </el-button>
          <el-button
            @click="handleGenTable(scope.row,1)"
            icon="el-icon-download"
            size="small"
            type="text"
            v-hasPermi="['tool:gen:code']">
            生成MyBatis代码
          </el-button>

          <el-button
            @click="handlePreview(scope.row,3)"
            icon="el-icon-view"
            size="small"
            type="text"
            v-hasPermi="['tool:gen:preview']">
            预览JPA-Front
          </el-button>
          <el-button
            @click="handleGenTable(scope.row,3)"
            icon="el-icon-download"
            size="small"
            type="text"
            v-hasPermi="['tool:gen:code']">
            生成JPA-Front代码
          </el-button>

          <el-button
            @click="handleEditTable(scope.row)"
            icon="el-icon-edit"
            size="small"
            type="text"
            v-hasPermi="['tool:gen:edit']"
          >编辑
          </el-button>

          <el-button
            @click="handleDelete(scope.row)"
            icon="el-icon-delete"
            size="small"
            type="text"
            v-hasPermi="['tool:gen:remove']"
          >删除
          </el-button>

        </template>
      </el-table-column>
    </el-table>
    <pagination
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
      v-show="total>0"
    />
    <!-- 预览界面 -->
    <el-dialog :title="preview.title" :visible.sync="preview.open" append-to-body top="5vh" width="80%">
      <el-tabs v-model="preview.activeName">
        <el-tab-pane
          :key="key"
          :label="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
          :name="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
          v-for="(value, key) in preview.data"
        >
          <pre>{{ value }}</pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
    <import-table @ok="handleQuery" ref="import"/>
  </div>
</template>

<script>
  import {delTable, listTable, previewTable} from "@/api/tool/gen";
  import importTable from "./importTable";
  import {downLoadZip} from "@/utils/zipdownload";

  export default {
    name: "Gen",
    components: {importTable},
    data() {
      return {
        // 遮罩层
        loading: true,
        // 唯一标识符
        uniqueId: "",
        // 选中数组
        ids: [],
        // 选中表数组
        tableNames: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 总条数
        total: 0,
        // 表数据
        tableList: [],
        // 日期范围
        dateRange: "",
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          tableName: undefined,
          tableComment: undefined
        },
        // 预览参数
        preview: {
          open: false,
          title: "代码预览",
          data: {},
          activeName: "service.java"
        }
      };
    },
    created() {
      this.getList();
    },
    activated() {
      const time = this.$route.query.t;
      if (time != null && time != this.uniqueId) {
        this.uniqueId = time;
        this.resetQuery();
      }
    },
    methods: {
      /** 查询表集合 */
      getList() {
        this.loading = true;
        listTable(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
            this.tableList = response.rows;
            this.total = response.total;
            this.loading = false;
          }
        );
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 生成代码操作 */
      handleGenTable(row,type) {
        const tableNames = row.tableName || this.tableNames;
        if (tableNames == "") {
          this.msgError("请选择要生成的数据");
          return;
        }
        downLoadZip("/tool/gen/batchGenCode?tables=" + tableNames + "&type="+type, "that-day");
      },
      /** 打开导入表弹窗 */
      openImportTable() {
        this.$refs.import.show();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.dateRange = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      /** 预览按钮 */
      handlePreview(row, type) {
        previewTable(row.tableId, type).then(response => {
          this.preview.data = response.data;
          this.preview.open = true;
        });
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.tableId);
        this.tableNames = selection.map(item => item.tableName);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },
      /** 修改按钮操作 */
      handleEditTable(row) {
        const tableId = row.tableId || this.ids[0];
        this.$router.push("/gen/edit/" + tableId);
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const tableIds = row.tableId || this.ids;
        this.$confirm('是否确认删除表编号为"' + tableIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delTable(tableIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      }
    }
  };
</script>
