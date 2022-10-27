// const PrerenderSPAPlugin = require("prerender-spa-plugin"); // 引入插件
// const Renderer = PrerenderSPAPlugin.PuppeteerRenderer;

const path = require("path");

// 网页授权地址：
// 接口安全域名：
const opt = {
    proxyTarget: "https://app.betswap.space/api/v1/",
    // proxyTarget: "http://52.74.13.176:8002/api/v1/",
    // proxyTarget: "http://101.200.220.144:8002/api/v1/",
    hostArr: [
        "localhost",
        "192.168.1.114",
    ],
    hostIndex: 1,
};
const Timestamp = new Date().getTime();  //当前时间为了防止打包缓存不刷新，所以给每个js文件都加一个时间戳

module.exports = {
    // 部署应用包时的基本 URL,从 Vue CLI 3.3 起已弃用baseUrl

    //配置根目录
    publicPath: process.env.NODE_ENV !== "production" ? "./" : '/', //本地开发用
    // publicPath: "skins/default/dist_lehuo",//打包用

    // build时构建文件的目录 构建时传入 --no-clean 可关闭该行为
    outputDir: "dist",

    // build时放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录
    // assetsDir: "skins/default/dist_lehuo",
    assetsDir: "assets",

    // 指定生成的 index.html 的输出路径 (相对于 outputDir)。也可以是一个绝对路径。
    indexPath: "index.html",

    // 默认在生成的静态资源文件名中包含hash以控制缓存
    filenameHashing: false,

    // 是否在开发环境下通过 eslint-loader 在每次保存时 lint 代码 (在生产构建时禁用 eslint-loader)
    lintOnSave: process.env.NODE_ENV !== "production",

    // 是否使用包含运行时编译器的 Vue 构建版本
    runtimeCompiler: false,

    // Babel 显式转译列表
    transpileDependencies: [],

    // 如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建
    productionSourceMap: false,

    // 设置生成的 HTML 中 <link rel="stylesheet"> 和 <script> 标签的 crossorigin 属性（注：仅影响构建时注入的标签）
    crossorigin: undefined,

    // 在生成的 HTML 中的 <link rel="stylesheet"> 和 <script> 标签上启用 Subresource Integrity (SRI)
    integrity: false,

    // 如果这个值是一个对象，则会通过 webpack-merge 合并到最终的配置中
    // 如果你需要基于环境有条件地配置行为，或者想要直接修改配置，那就换成一个函数 (该函数会在环境变量被设置之后懒执行)。该方法的第一个参数会收到已经解析好的配置。在函数内，你可以直接修改配置，或者返回一个将会被合并的对象

    configureWebpack: (config) => {
        return {
            name: "",
            resolve: {
                // 配置解析别名
                extensions: [".js", ".vue", ".json"],
                alias: {
                    "@": path.resolve(__dirname, "./src"),
                    "@img": path.resolve(__dirname, "./src/assets/img"),
                    "@tabbar": path.resolve(__dirname, "./src/assets/img/tabbar"),
                    vue$: "vue/dist/vue.esm.js",
                    api: path.resolve(__dirname, "../src/api"),
                },
            },
            optimization: {
                splitChunks: {
                    chunks: "all",
                },
            },
            externals: {
                // 'echarts': 'echarts',
            },

            output: { // 输出重构  打包编译后的 文件名称  【模块名称.版本号.时间戳】
                filename: `assets/js/[name].${Timestamp}.js`,
                chunkFilename: `assets/js/[name].${Timestamp}.js`
            },

            //配置打包模式[] ||
            plugins: process.env.NODE_ENV !== "production" ? [] : [] || [
                new PrerenderSPAPlugin({
                staticDir: path.join(__dirname, "dist_mingde"),
                // 对应自己的路由文件，比如about有参数，就需要写成 /about/param1。
                routes: [
                    // 第一组
                    // "/",
                ],

                //  必须配置不然不会进行预编译
                renderer: new Renderer({
                    inject: {
                    foo: "bar",
                    },
                    headless: false,
                    renderAfterTime: 5000,
                    captureAfterTime: 600000,
                }),

            })]

        };
    },

    devServer: {
        open: false, //是否启动打开浏览器
        // host: '192.168.1.113',
        host: '0.0.0.0',
        disableHostCheck: true,
        port: 8000,
        https: false,
        overlay: {
            warnings: false,
            errors: true,
        },
        proxy: {
            "/api": {
                target: opt.proxyTarget,
                ws: true,
                changeOrigin: true,// 如果接口跨域，需要进行这个参数配置
                pathRewrite: {
                    "^/api": "",
                },
            },
        },
    },

    css: {
        // 是否使用css分离插件 ExtractTextPlugin
        extract: {
            // 修改打包后css文件名
            filename: `assets/css/[name].${Timestamp}.css`,
            chunkFilename: `assets/css/[name].${Timestamp}.css`
        }
    },

    // pluginOptions: {
    //     "style-resources-loader": {
    //         preProcessor: "less",
    //         patterns: [path.resolve(__dirname, "./src/common/variable.less")], // 引入全局样式变量
    //     },
    // },
};
