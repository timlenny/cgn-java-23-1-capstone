import React, {MouseEventHandler, useCallback, useEffect, useState} from 'react';
import ReactFlow, {addEdge, NodeMouseHandler, NodeTypes, useEdgesState, useNodesState} from 'reactflow';
import 'reactflow/dist/style.css';
import '../../style/home/reactFlow.css'
import UseGetTopic from "../../hook/topic/UseGetTopic";
import {edgesType} from "../../model/topic/Edge";
import '../../style/home/Home.css';
import {useNavigate} from "react-router-dom";
import CustomLabelNode from "./CustomNode";
import DefaultNode from "./DefaultNode";
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import AddIcon from '@mui/icons-material/Add';
import UseDeleteTopic from "../../hook/topic/UseDeleteTopic";
import SaveIcon from '@mui/icons-material/Save';
import UseUpdateTopicPosition from "../../hook/topic/UseUpdateTopicPosition";
import useAuthRedirect from "../../hook/auth/UseAuthRedirect";
import HomeHeaderBox from "./HomeHeaderBox";

const nodeTypes: NodeTypes = {
    customLabelNode: CustomLabelNode,
    defaultNode: DefaultNode,
};

export type nodeType = {
    id: string,
    type?: string,
    position: { x: number, y: number },
    data: { label: string, size: number }
}

export default function HomePage() {

    const navigate = useNavigate()
    const {getAllTopics, topic} = UseGetTopic();
    const {deleteSingleTopic, deleteStatus} = UseDeleteTopic();
    let buildListEdges: edgesType[] = []
    let buildListNodes: nodeType[] = []
    const updateTopicPosition = UseUpdateTopicPosition();
    const [nodes, setNodes, onNodesChange] = useNodesState(buildListNodes);
    const [nodesBackup, setNodesBackup] = useNodesState(buildListNodes);
    const [changedNodes, setChangedNodes] = useNodesState(buildListNodes);
    const [edges, setEdges, onEdgesChange] = useEdgesState(buildListEdges);
    const [deleteMode, setDeleteMode] = useState(false);
    const [changeMode, setChangeMode] = useState(false);
    const [dragStartTime, setDragStartTime] = useState<number | null>(null);
    const onConnect = useCallback((params: any) => setEdges((eds) => addEdge(params, eds)), [setEdges]);
    useAuthRedirect()
    useEffect(() => {
        getAllTopics();
    }, [getAllTopics, deleteStatus])

    useEffect(() => {
        let buildListNodes = topic.map((data) => {
            const newNode: nodeType = {
                id: data.id,
                type: data.topicName === 'HOME' ? 'customLabelNode' : 'defaultNode',
                position: {x: data.position.x, y: data.position.y},
                data: {label: data.topicName, size: data.size},
            }
            return newNode;
        })
        setNodes(buildListNodes)
        setNodesBackup(buildListNodes);

        let buildListEdges = topic.map((data) => {
            return data.edges.map((edge) => {
                const newEdge: edgesType = {id: edge.id, source: edge.source, target: edge.target};
                return newEdge;
            })
          
        }).flat()
        setEdges(buildListEdges)

        if (buildListNodes.length < 1) {
            let offlineNode = [
                {id: '1', position: {x: 125, y: 250}, data: {label: 'Loading...', size: 3}}
            ];
            setNodes(offlineNode)
        }
    }, [topic, setEdges, setNodes, setNodesBackup])

    const handleSubmitAdd: MouseEventHandler<HTMLButtonElement> = () => {
        navigate("/topic/add")
    }

    const onNodeClickDelete: NodeMouseHandler = (event, node) => {
        if (deleteMode && node.data.label !== "HOME") {
            deleteSingleTopic(node.id);
        }
    }

    function displayHint() {
        if (deleteMode) {
            return (<p className={"hint-text"}>CLICK TOPIC TO DELETE</p>)
        }
    }

    function displaySaveChanges() {
        if (changeMode) {
            return (<button className="homeButtonSave" onClick={() => {
                updateTopicPosition(changedNodes);
                setNodesBackup(nodes)
                setChangeMode(!changeMode)
            }}>
                <SaveIcon sx={{fontSize: 35}}/>
            </button>)
        }
    }

    useEffect(() => {
        const changedNodes = nodes.filter((node) => {
            return !nodesBackup.includes(node)
        })
        setChangedNodes(changedNodes);
    }, [nodes, nodesBackup, setChangedNodes])


    return (
        <div style={{width: '100vw', height: '100vh'}}>
            {displayHint()}
            {displaySaveChanges()}
            <HomeHeaderBox/>
            <button className="homeButtonAdd" onClick={handleSubmitAdd}>
                <AddIcon sx={{fontSize: 35}}/>
            </button>
            <button className={deleteMode ? "homeButtonDel-active" : "homeButtonDel"} onClick={() => {
                setDeleteMode(deleteMode => !deleteMode)
            }}>
                <DeleteOutlineIcon sx={{fontSize: 30, color: "#1B334C"}}/>
            </button>
            <ReactFlow
                nodes={nodes}
                edges={edges}
                nodeTypes={nodeTypes}
                onNodesChange={onNodesChange}
                onEdgesChange={onEdgesChange}
                onConnect={onConnect}
                onNodeClick={onNodeClickDelete}
                onNodeDragStart={() => {
                    setDragStartTime(Date.now());
                }}
                onNodeDragStop={(event, node) => {
                    const dragDuration =
                        dragStartTime !== null ? Math.max((Date.now() - dragStartTime) / 1000, 0) : 0;
                    if (dragDuration > 0.19) {
                        deleteMode ? setChangeMode(false) : setChangeMode(true);
                    } else {
                        if (!deleteMode)
                            navigate("/subtopic/" + node.id)
                    }
                }}
            />
        </div>
    );
}