<template></template>

<script>
export default {
  mounted() {
    const that = this;
    window.onresize = () => {
      return () => {
        window.screenWidth = window.innerWidth;
        that.screenWidth = window.screenWidth;
      };
    };
  },

  watch: {
    screenWidth(val) {
      this.screenWidth = val;
      this.showOnScreen = this.screenWidth >= 1919;
    },
  },

  data: () => ({
    screenWidth: window.innerWidth,
    showOnScreen: window.innerWidth >= 1919,
  }),
};
</script>
