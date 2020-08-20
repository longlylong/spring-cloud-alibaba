import JSEncrypt from 'jsencrypt/bin/jsencrypt'

// 密钥对生成 http://web.chacuo.net/netrsakeypair

const publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdC8fp9HEYdDMA6vxweBecPFeQULLbhZHhGu45WLUtrsvxFXE+Vs4dah+4XOBSS+P7Tu364ziWN9tvtjDQVWMpAgSqb618gU8LTcWhes01tcBiWim80ZePTZfD63kJNjeoOaTBdbpCRQEdtanWqj+g5bzsho0YhJCsAhPhZuzhGwIDAQAB'

const privateKey = ''

// 加密
export function encrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对需要加密的数据进行加密
}

// 解密
export function decrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey)
  return encryptor.decrypt(txt)
}

