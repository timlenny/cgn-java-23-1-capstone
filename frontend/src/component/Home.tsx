import React, {useCallback, useEffect} from 'react';
import ReactFlow, {addEdge, useEdgesState, useNodesState} from 'reactflow';
import 'reactflow/dist/style.css';
import '../style/reactFlow.css'
import UseGetTopic from "../hook/UseGetTopic";
import {edgesType} from "../model/Edge";

type nodeType = {
    id: string,
    position: { x: number, y: number },
    data: { label: string }
}

export default function Home() {

    const {getAllTopics, topic} = UseGetTopic();
    let buildListEdges: edgesType[] = []
    let buildListNodes: nodeType[] = []

    const [nodes, setNodes, onNodesChange] = useNodesState(buildListNodes);
    const [edges, setEdges, onEdgesChange] = useEdgesState(buildListEdges);
    const onConnect = useCallback((params: any) => setEdges((eds) => addEdge(params, eds)), [setEdges]);

    useEffect(() => {
        getAllTopics();
    }, [getAllTopics])

    useEffect(() => {
        let buildListNodes = topic.map((data) => {
            const newNode: nodeType = {
                id: data.id,
                position: {x: data.position.x, y: data.position.y},
                data: {label: data.topicName}
            }
            return newNode;
        })
        setNodes(buildListNodes)

        let buildListEdges = topic.map((data) => {
            const collEdges = data.edges.map((edge) => {
                const newEdge: edgesType = {id: edge.id, source: edge.source, target: edge.target};
                return newEdge;
            })
            return collEdges;
        }).flat()
        setEdges(buildListEdges)

        if (buildListNodes.length < 1) {
            let offlineNode = [
                {id: '1', position: {x: 300, y: 500}, data: {label: 'START'}},
            ];
            setNodes(offlineNode)
        }
    }, [topic, setEdges, setNodes])

    return (
        <div style={{width: '100vw', height: '100vh'}}>
            <ReactFlow
                nodes={nodes}
                edges={edges}
                onNodesChange={onNodesChange}
                onEdgesChange={onEdgesChange}
                onConnect={onConnect}
            />
        </div>
    );
}