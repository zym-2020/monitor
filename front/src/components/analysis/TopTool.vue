<template>
  <div class="top-tool">
    <div class="base-function">
      <el-button type="primary" text @click="dialogAddData = true"
        ><svg style="width: 16px; height: 16px; margin-right: 10px">
          <use xlink:href="#icon-tianjiayingyong"></use></svg
        >添加</el-button
      >
      <el-divider direction="vertical" />
      <el-button type="primary" text @click="dialogAnalyse = true"
        ><svg style="width: 16px; height: 16px; margin-right: 10px">
          <use xlink:href="#icon-shujuyanjiu"></use></svg
        >河床分析</el-button
      >
    </div>

    <div class="map-tool">
      <el-button type="primary" :text="state === 1 ? false : true" @click="sectionClick"
        ><svg style="width: 16px; height: 16px; margin-right: 10px">
          <use xlink:href="#icon-xian"></use></svg
        >绘制断面</el-button
      >
      <el-divider direction="vertical" />
      <el-button type="primary" :text="state === 2 ? false : true" @click="regionClick"
        ><svg style="width: 16px; height: 16px; margin-right: 10px">
          <use xlink:href="#icon-mian"></use></svg
        >绘制区域</el-button
      >
    </div>

    <div class="right">
      <el-button type="info" @click="flowClick">流场动画</el-button>
    </div>

    <el-dialog v-model="dialogAddData" width="1000px" title="添加数据">
      <add-data-dialog @returnData="returnData" v-if="dialogAddData" />
    </el-dialog>

    <el-dialog v-model="dialogAnalyse" width="700px" title="河床分析">
      <analyse-dialog @analyse="analyse" v-if="dialogAnalyse" />
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import AddDataDialog from "./AddDataDialog.vue";
import AnalyseDialog from "./AnalyseDialog.vue";
import router from "@/router";
export default defineComponent({
  components: {
    AddDataDialog,
    AnalyseDialog,
  },
  emits: ["returnFileList", "operateDraw", "analyse"],
  setup(_, context) {
    const dialogAddData = ref(false);
    const dialogAnalyse = ref(false);
    const state = ref(0);

    const returnData = (
      val: {
        fileId: string;
        fileName: string;
        dataListId: string;
        dataListName: string;
        visualType: string;
        visualId: string;
      }[]
    ) => {
      dialogAddData.value = false;
      context.emit("returnFileList", val);
    };

    const sectionClick = () => {
      if (state.value === 1) {
        state.value = 0;
      } else {
        state.value = 1;
      }
      context.emit("operateDraw", state.value);
    };

    const regionClick = () => {
      if (state.value === 2) {
        state.value = 0;
      } else {
        state.value = 2;
      }
      context.emit("operateDraw", state.value);
    };

    const analyse = (val: { type: string; value: any }) => {
      context.emit("analyse", val);
      dialogAnalyse.value = false;
    };

    const flowClick = () => {
      router.push({ path: "/flow" });
    };

    return {
      state,
      dialogAddData,
      returnData,
      sectionClick,
      regionClick,
      dialogAnalyse,
      analyse,
      flowClick,
    };
  },
});
</script>

<style lang="scss" scoped>
.top-tool {
  height: 50px;
  display: flex;
  background: #f6f7f8;
  border-bottom: solid 0.5px #dcdfe6;
  box-sizing: border-box;
  position: relative;
  .base-function {
    margin-left: 70px;
    height: 100%;
    line-height: 50px;
  }
  .map-tool {
    height: 100%;
    position: absolute;
    left: 35%;
    line-height: 50px;
    .el-button {
      border: solid 1px rgba($color: #000000, $alpha: 0);
      box-sizing: border-box;
    }
  }
  .right {
    height: 100%;
    right: 30px;
    position: absolute;
    top: 10px;
    cursor: pointer;
    .el-dropdown {
      margin-top: 18px;
    }
  }

  :deep().el-dialog {
    .el-dialog__header {
      padding: 10px;
      margin: 0;
      background: #000000;
      .el-dialog__title {
        color: white;
      }
      .el-dialog__headerbtn {
        height: 40px;
        .el-icon {
          color: white;
        }
      }
    }
    .el-dialog__body {
      padding: 0;
    }
  }
}
</style>
