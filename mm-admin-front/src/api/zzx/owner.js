import request from '@/utils/request'

// 查询房东登记列表
export function listOwner(query) {
    return request({
        url: '/zzx/owner/list',
        method: 'get',
        params: query
    })
}

// 查询房东登记详细
export function getOwner(id) {
    return request({
        url: '/zzx/owner/' + id,
        method: 'get'
    })
}

// 新增房东登记
export function addOwner(data) {
    return request({
        url: '/zzx/owner',
        method: 'post',
        data: data
    })
}

// 修改房东登记
export function updateOwner(data) {
    return request({
        url: '/zzx/owner',
        method: 'put',
        data: data
    })
}

// 删除房东登记
export function delOwner(id) {
    return request({
        url: '/zzx/owner/' + id,
        method: 'delete'
    })
}

// 导出房东登记
export function exportOwner(query) {
    return request({
        url: '/zzx/owner/export',
        method: 'get',
        params: query
    })
}
