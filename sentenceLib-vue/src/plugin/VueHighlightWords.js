/**
 * 转义正则的特殊字符
 * @param {string} str 
 * @return {string}
 */
const escapeReg = (str) => {
    return str.replace(/[\.\\\*\(\)\?\^\$\+\|\{\}\[\]\-\/\<\>]/g, '\\$&');
}

/**
 * 是否是Object
 * @param {*} value 
 */
const isObject = (value) => {
    return Object.prototype.toString.call(value).includes('Object');
}

/**
 * 初始化样式，可传入字符串和对象
 * @param {object|string} style
 * @return {string}
 */
const initStyle = (style) => {
    if (isObject(style)) {
        let ret = '';

        for (let key in style) {
            ret += `${ key }: ${ style[key] }; `;
        }

        return ret;
    }

    if (typeof style === 'string') {
        return style;
    }

    return '';
}

/**
 * 处理对象类型的classname
 * @param {*} classname 
 * @return {string}
 */
const initObjClass = (classname) => {
    let ret = [];
    for (let key in classname) {
        if (classname[key]) {
            ret.push(key);
        }
    }

    return ret.join(' ');
}

/**
 * 初始化类名，可传入String|Array<String|Object>|Object
 * @param {*} classname 
 * @return {string}
 */
const initClassname = (classname) => {
    /* 对象。只取真值 */
    if (isObject(classname)) {
        return initObjClass(classname);
    }

    if (Array.isArray(classname)) {
        return classname.map(cn => {
            if (isObject(cn)) {
                return initObjClass(cn);
            }

            return typeof cn === 'string' ? cn : '';
        }).join(' ');
    }

    return typeof classname === 'string' ? classname : '';
}

/**
 * 格式化关键词
 * 传入为数组时，直接返回
 * 传入为字符串|数字时，当做字符串处理，用分隔符分隔字符串
 * @param {*} keyword 
 * @param {*} separator 默认使用空格分隔
 */
const initKeyword = (keyword, separator = ' ') => {
    if (Array.isArray(keyword)) {
        return keyword;
    }

    if (isObject(keyword) || ['boolean', 'undefined', 'symbol'].includes(typeof keyword)) {
        return [];
    }

    let ret = [];

    if (!separator) {
        ret = [keyword];
    } else {
        ret = (keyword + '').split(separator);
    }

    return ret.filter(Boolean).sort((a, b) => b.length - a.length);
}

/**
 * 重置el的html内容
 * @param {*} el 
 */
const resetHtml = (el) => {
    const originEle = el;
    if(! originEle.highlightEl) {
        return;
    }
    delete originEle.style.display;
    originEle.highlightEl.remove();
    originEle.highlightEl = '';
}

/**
 * directive必须传入一个对象
 * value的参数：{ 
 *     keyword: [string, number, array], 用于筛选的关键词
 *     separator: string, 分隔符，仅当传入的keyword是字符串时有效
 *     caseSensitive: bool, 是否大小写敏感
 *     className: [object, array<string|object>, object], 用于高亮的类名
 *     style: [object, string], 用于高亮的样式
 * }
 * @param {*} el 
 * @param {*} binding 
 * @param {*} vnode 
 * @param {*} options
 */
const handle = (el, binding, vnode, options) => {
    if (! vnode.context || ! isObject(binding.value)) {
        return;
    }

    const params = {
        ...options,
        ...binding.value
    };

    const { 
        keyword,
        separator, 
        caseSensitive, 
        className = '', 
        style = ''
    } = params;
    const queries = initKeyword(keyword, separator);

    if (queries.length === 0) {
        return;
    }

    const pattern = queries.map(k => `(${escapeReg(k)})`).join('|');
    const reg = new RegExp(pattern, !caseSensitive ? 'ig' : 'g');
    const originEle = el; /* 存储原始数据的节点 */
    if(! originEle.innerText || ! originEle.parentElement) {
        return;
    }
    if(originEle.childElementCount) {
        console.erro('不允许在非纯文本节点（即没有子元素）下使用highlight指令');
        return;
    }
    if(! originEle.highlightEl) {
        originEle.highlightEl = originEle.cloneNode();
        originEle.parentElement.insertBefore(originEle.highlightEl, originEle);
    }
    const hlStyle = initStyle(style);
    const hlClassName = initClassname(className);
    delete originEle.style.display;
    // 如果不会显示出来，vue默认不会动态更新数据，所以nextTick一下等动态更新完毕了才进行隐藏
    vnode.context.$nextTick(() => {
        originEle.highlightEl.innerHTML = originEle.innerText.replace(reg, (i) => {
            return `<strong class="${hlClassName}" style="${hlStyle}">${i}</strong>`;
        });
        originEle.style.display = 'none';
    });
}

/**
 * 拷贝一下仓库的代码进行修改的，原代码直接使用无法支持动态更新的内容
 * @Copy https://github.com/M-FE/vue-words-highlight/blob/a28220e2dbaabd4b8c9f92f0edcc87a0181f5789/src/util.js
 */
const VueHignlightWords = {
    install(Vue, options) {
        Vue.directive('highlight', {
            bind(el, binding, vnode) {
                handle(el, binding, vnode, options);
            },
            update(el, binding, vnode) {
                handle(el, binding, vnode, options);
            },
            unbind(el) {
                resetHtml(el);
            }
        });
    }
};
export default VueHignlightWords;