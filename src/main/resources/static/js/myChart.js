"use strict";
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));

// 指定图表的配置项和数据
// var option = {
//     title: {
//         text: 'Graph 简单示例'
//     },
//     tooltip: {},
//     animationDurationUpdate: 1500,
//     animationEasingUpdate: 'quinticInOut',
//     series: [
//         {
//             type: 'graph',
//             layout: 'none',
//             symbolSize: 50,
//             roam: true,
//             label: {
//                 normal: {
//                     show: true
//                 }
//             },
//             edgeSymbol: ['circle', 'arrow'],
//             edgeSymbolSize: [4, 10],
//             edgeLabel: {
//                 normal: {
//                     textStyle: {
//                         fontSize: 20
//                     }
//                 }
//             },
//             data: [{
//                 name: '节点1',
//                 x: 300,
//                 y: 300
//             }, {
//                 name: '节点2',
//                 x: 800,
//                 y: 300
//             }, {
//                 name: '节点3',
//                 x: 550,
//                 y: 100
//             }, {
//                 name: '节点4',
//                 x: 550,
//                 y: 500
//             }],
//             // links: [],
//             links: [{
//                 source: 0,
//                 target: 1,
//                 symbolSize: [5, 20],
//                 label: {
//                     normal: {
//                         show: true
//                     }
//                 },
//                 lineStyle: {
//                     normal: {
//                         width: 5,
//                         curveness: 0.2
//                     }
//                 }
//             }, {
//                 source: '节点2',
//                 target: '节点1',
//                 label: {
//                     normal: {
//                         show: true
//                     }
//                 },
//                 lineStyle: {
//                     normal: {curveness: 0.2}
//                 }
//             }, {
//                 source: '节点1',
//                 target: '节点3'
//             }, {
//                 source: '节点2',
//                 target: '节点3'
//             }, {
//                 source: '节点2',
//                 target: '节点4'
//             }, {
//                 source: '节点1',
//                 target: '节点4'
//             }],
//             lineStyle: {
//                 normal: {
//                     opacity: 0.9,
//                     width: 2,
//                     curveness: 0
//                 }
//             }
//         }
//     ]
// };



function createNodes(count) {
    var nodes = [];
    for (var i = 0; i < count; i++) {
        nodes.push({
            id: i,
            name:'节点'+i,
            symbolSize: i+20
        });
    }
    return nodes;
}

function createEdges(count) {
    var edges = [];
    if (count === 2) {
        return [[0, 1]];
    }
    for (var i = 0; i < count; i++) {
        edges.push([i, (i + 1) % count]);
    }
    return edges;
}

var datas = [];
// for (var i = 0; i < 16; i++) {
//     datas.push({
//         nodes: createNodes(i + 2),
//         edges: createEdges(i + 2)
//     });
// }


datas.push({
    nodes: createNodes(12),
    edges: createEdges(12)
});



var option = {
    series: datas.map(function (item, idx) {
        return {
            type: 'graph',
            layout: 'force',
            symbolSize: 20,
            animation: false,
            data: item.nodes,
            left: (idx % 4) * 25 + '%',
            top: Math.floor(idx / 4) * 25 + '%',
            width: '100%',
            height: '100%',
            edgeSymbol: ['circle', 'arrow'],
            edgeSymbolSize: [4, 10],
            edgeLabel: {
                normal: {
                    textStyle: {
                        fontSize: 20
                    }
                }
            },
            force: {
                // initLayout: 'circular'
                // gravity: 0
                repulsion: 240
            },
            edges: [{
                source: 0,
                target: 1,
                symbolSize: [5, 20],
                lineStyle: {
                    normal: {
                        width: 5,
                        curveness: 0.2
                    }
                }
            },{
                source: 2,
                target: 1
            },
            ]
            // edges: item.edges.map(function (e) {
            //     return {
            //         // source: e[0],
            //         // target: e[1]
            //     };
            // })
        };
    })
};



myChart.setOption(option);
