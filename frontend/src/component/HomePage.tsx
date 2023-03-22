import React, {MouseEventHandler, useCallback, useEffect} from 'react';
import ReactFlow, {addEdge, NodeTypes, useEdgesState, useNodesState} from 'reactflow';
import 'reactflow/dist/style.css';
import '../style/home/reactFlow.css'
import UseGetTopic from "../hook/UseGetTopic";
import {edgesType} from "../model/topic/Edge";
import '../style/home/Home.css';
import {useNavigate} from "react-router-dom";
import CustomLabelNode from "./CustomNode";
import DefaultNode from "./DefaultNode";

const nodeTypes: NodeTypes = {
    customLabelNode: CustomLabelNode,
    defaultNode: DefaultNode,
};

type nodeType = {
    id: string,
    type?: string,
    position: { x: number, y: number },
    data: { label: string }
}



export default function HomePage() {

    const navigate = useNavigate()
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
                type: data.topicName === 'HOME' ? 'customLabelNode' : 'defaultNode',
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
                {id: '1', position: {x: 125, y: 250}, data: {label: 'Loading...'}},
            ];
            setNodes(offlineNode)
        }
    }, [topic, setEdges, setNodes])

    const handleSubmitAdd: MouseEventHandler<HTMLButtonElement> = () => {
        navigate("/topic/add")
    }

    return (
        <div style={{width: '100vw', height: '100vh'}}>
            <button className="addButton" onClick={handleSubmitAdd}></button>
            <ReactFlow
                nodes={nodes}
                edges={edges}
                nodeTypes={nodeTypes}
                onNodesChange={onNodesChange}
                onEdgesChange={onEdgesChange}
                onConnect={onConnect}
            />
        </div>
    );
}