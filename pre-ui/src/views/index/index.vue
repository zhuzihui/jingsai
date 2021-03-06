<template>
  <div class="wel-contailer">
    <div class="banner-text">
      <span>
        主页
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Index',
  data() {
    return {
      activeNames: ['1', '2', '3', '4'],
      DATA: [],
      text: '',
      actor: '',
      count: 0,
      isText: false
    }
  },

  methods: {
  }
}
</script>

<style scoped="scoped" lang="scss">
  .wel-contailer {
    position: relative;
    width: 100%;
    height: 100%;
    padding: 16px;
    background-color: #fff;
  }

  .banner-text {
    position: relative;
    padding: 0 20px;
    font-size: 20px;
    text-align: center;
    color: #333;
  }

  .banner-img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0.8;
    display: none;
  }

  .actor {
    height: 250px;
    overflow: hidden;
    font-size: 18px;
    color: #333;
  }

  .actor:after {
    content: '';
    width: 3px;
    height: 25px;
    vertical-align: -5px;
    margin-left: 5px;
    background-color: #333;
    display: inline-block;
    animation: blink 0.4s infinite alternate;
  }

  .typeing:after {
    animation: none;
  }

  @keyframes blink {
    to {
      opacity: 0;
    }
  }
</style>
