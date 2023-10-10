<template>
    <div>
        <div>大家好!我的名字是{{name}},年龄为{{age}}岁.</div>
        <div>学号：{{stu.No}}</div>
        <div>学校：{{stu.schoolName}}</div>
        <div>专业：{{stu.majorName}}</div>
    </div>
</template>

<script>
    export default {
        name: "MyComponent",
        props: {
            name: String,
            age: Number,
            stu: Object
        }
    }
</script>

<style scoped>

</style>
