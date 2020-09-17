<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <el-input v-model="query.name" clearable size="small" placeholder="请输入表名" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation />
      </div>
      <crudOperation>
        <el-tooltip slot="right" class="item" effect="dark" content="数据库中表字段变动时使用该功能" placement="top-start">
          <el-button
            class="filter-item"
            size="mini"
            type="success"
            icon="el-icon-refresh"
            :loading="syncLoading"
            :disabled="crud.selections.length === 0"
            @click="sync"
          >同步</el-button>
        </el-tooltip>
      </crudOperation>
    </div>
    <!--表格渲染-->
    <el-table ref="table" v-loading="crud.loading" :data="crud.data" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
      <el-table-column type="selection" width="55" />
      <el-table-column label="代码生成" width="160px">
        <template slot-scope="scope">
          <el-button v-if="scope.row.isConfigured" size="mini" style="margin-right: 2px" type="text">
            <router-link :to="'system/tool/generator/preview/' + scope.row.tableName">
              预览
            </router-link>
            <!--            <router-link :to="{name:'preview',params:{tableName:scope.row.tableName,optType:1}}">-->
            <!--              预览-->
            <!--            </router-link>-->
          </el-button>
          <el-button
            v-if="scope.row.isConfigured"
            size="mini"
            style="margin-left: -1px;margin-right: 2px"
            type="text"
            @click="toDownload(scope.row.tableName,2)"
          >下载
          </el-button>
          <el-button size="mini" style="margin-left: -1px;margin-right: 2px" type="text">
            <router-link :to="'system/tool/generator/config/' + scope.row.tableName">
              配置
            </router-link>
          </el-button>
          <el-button
            v-if="scope.row.isConfigured"
            type="text"
            style="margin-left: -1px"
            size="mini"
            @click="toGen(scope.row.tableName)"
          >生成
          </el-button>
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" prop="tableName" label="表名" />
      <el-table-column :show-overflow-tooltip="true" prop="remark" label="备注" />
      <el-table-column prop="createTime" label="创建日期">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <!--      <el-table-column :show-overflow-tooltip="true" prop="engine" label="数据库引擎" />-->
      <!--      <el-table-column :show-overflow-tooltip="true" prop="coding" label="字符编码集" />-->
    </el-table>
    <!--分页组件-->
    <pagination />
  </div>
</template>

<script>

import { generator, sync } from '@/api/generator/generator'
import { downloadFile } from '@/utils/index'
import CRUD, { header, presenter } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import pagination from '@crud/Pagination'

export default {
  name: 'GeneratorIndex',
  components: { pagination, crudOperation, rrOperation },
  cruds() {
    return CRUD({ url: 'api/generator/tables' })
  },
  mixins: [presenter(), header()],
  data() {
    return {
      syncLoading: false
    }
  },
  created() {
    this.crud.optShow = { add: false, edit: false, del: false, download: false }
  },
  methods: {
    toGen(tableName) {
      // 生成代码
      generator(tableName, 0).then(data => {
        this.$notify({
          title: '生成成功',
          type: 'success',
          duration: 2500
        })
      })
    },
    toDownload(tableName, type) {
      // 打包下载
      generator(tableName, type).then(data => {
        downloadFile(data, tableName, 'zip')
      })
    },
    sync() {
      const tables = []
      this.crud.selections.forEach(val => {
        tables.push(val.tableName)
      })
      this.syncLoading = true
      sync(tables).then(() => {
        this.crud.refresh()
        this.crud.notify('同步成功', CRUD.NOTIFICATION_TYPE.SUCCESS)
        this.syncLoading = false
      }).then(() => {
        this.syncLoading = false
      })
    }
  }
}
</script>

<style scoped>

</style>
