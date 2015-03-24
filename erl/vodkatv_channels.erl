-module(vodkatv_channels).

-include_lib("eqc/include/eqc.hrl").
-include("measure.hrl").

-compile(export_all).

-define(MAX_CHANNELS, 1000).
-define(STEP, 100).

%%
%% Grow functions
%%
grow_channels(NumChannels) ->
  [NumChannels + ?STEP].

grow_epg({NumChannels, _NumEPGChannels}) ->
  NumChannels1 = NumChannels + ?STEP,
  [{NumChannels1, 0},
   {NumChannels1, (random:uniform(?MAX_CHANNELS + 1)-1)},
   {NumChannels1, NumChannels1}].

grow_memcached(NumChannels) ->
  [NumChannels + ?STEP].

%%
%% Eval cmds functions
%%
eval_cmds_channels(NumChannels) ->
    SetupCommands = get_setup_commands_channels(),
    RunCommands = get_run_commands_channels(NumChannels),
    TearDownCommands = get_teardown_commands_channels(),
    measure_java:run_java_commands(false, 5, SetupCommands,
        lists:flatten(RunCommands), TearDownCommands).

eval_cmds_epg({NumChannels, NumEPGChannels}) ->
    SetupCommands = get_setup_commands_epg(NumEPGChannels),
    RunCommands = get_run_commands_epg(NumChannels),
    TearDownCommands = get_teardown_commands_epg(),
    measure_java:run_java_commands(false, 5, SetupCommands,
        lists:flatten(RunCommands), TearDownCommands).

eval_cmds_memcached(NumChannels) ->
    SetupCommands = get_setup_commands_memcached(),
    RunCommands = get_run_commands_memcached(NumChannels),
    TearDownCommands = get_teardown_commands_memcached(),
    measure_java:run_java_commands(false, 5, SetupCommands,
        lists:flatten(RunCommands), TearDownCommands).

eval_cmds_memcached_warmup(NumChannels) ->
    SetupCommands = get_setup_commands_memcached_warmup(NumChannels),
    RunCommands = get_run_commands_memcached(NumChannels),
    TearDownCommands = get_teardown_commands_memcached(),
    measure_java:run_java_commands(false, 5, SetupCommands,
        lists:flatten(RunCommands), TearDownCommands).

%%
%% Measure size functions
%%
measure_size_channels(NumChannels) ->
    NumChannels.

measure_size_epg({NumChannels, _NumEPGChannels}) ->
    NumChannels.

measure_size_memcached(NumChannels) ->
    NumChannels.

%%
%% Java commands
%%
get_java_code(Commands) ->
    ["{",
        "try {"
            "VoDKATVChannels v = VoDKATVChannels.getInstance();",
            Commands,
        "} catch(Exception e) {",
            "e.printStackTrace();",
        "}",
        "return null;",
    "}"].

%%
%% Java commands channels
%%
get_run_commands_channels(NumChannels) ->
    Commands = get_java_code([
        "v.findChannelDatas(" ++ integer_to_list(NumChannels) ++ ");"
    ]),
    lists:flatten(Commands).

get_setup_commands_channels() ->
    Commands = get_java_code([
        ";"
    ]),
    lists:flatten(Commands).

get_teardown_commands_channels() ->
    Commands = get_java_code([
        ";"
    ]),
    lists:flatten(Commands).

global_setup_channels() ->
    Commands = get_java_code([
        "v.globalSetUp(false);",
        "v.setUp(0);"
    ]),
    measure_java:run_java_commands(false, 1, null,
        lists:flatten(Commands), null).

global_teardown_channels() ->
    Commands = get_java_code([
        "v.tearDown();",
        "v.globalTearDown(false);"
    ]),
    measure_java:run_java_commands(false, 1, null,
        lists:flatten(Commands), null).

%%
%% Java commands EPG
%%
get_run_commands_epg(NumChannels) ->
    Commands = get_java_code([
        "v.findChannelsInformation(" ++ integer_to_list(NumChannels) ++ ");"
    ]),
    lists:flatten(Commands).

get_setup_commands_epg(NumEPGChannels) ->
    Commands = get_java_code([
        "v.setUp(" ++ integer_to_list(NumEPGChannels) ++ ");"
    ]),
    lists:flatten(Commands).

get_teardown_commands_epg() ->
    Commands = get_java_code([
        "v.tearDown();"
    ]),
    lists:flatten(Commands).

global_setup_epg() ->
    Commands = get_java_code([
        "v.globalSetUp(false);"
    ]),
    measure_java:run_java_commands(false, 1, null,
        lists:flatten(Commands), null).

global_teardown_epg() ->
    Commands = get_java_code([
        "v.globalTearDown(false);"
    ]),
    measure_java:run_java_commands(false, 1, null,
        lists:flatten(Commands), null).

%%
%% Java commands Memcached
%%
get_run_commands_memcached(NumChannels) ->
    Commands = get_java_code([
        "v.findChannelsInformation(" ++ integer_to_list(NumChannels) ++ ");"
    ]),
    lists:flatten(Commands).

get_setup_commands_memcached() ->
    Commands = get_java_code([
        ";"
    ]),
    lists:flatten(Commands).

get_setup_commands_memcached_warmup(NumChannels) ->
    Commands = get_java_code([
        "/* Warm up caches */",
        "try { v.findChannelsInformation(" ++
            integer_to_list(NumChannels) ++
            "); } catch(Exception e) {;}"
    ]),
    lists:flatten(Commands).

get_teardown_commands_memcached() ->
    Commands = get_java_code([
        ";"
    ]),
    lists:flatten(Commands).

global_setup_memcached() ->
    Commands = get_java_code([
        "v.globalSetUp(true);",
        "v.setUp(1000);"
    ]),
    measure_java:run_java_commands(false, 1, null,
        lists:flatten(Commands), null).

global_teardown_memcached() ->
    Commands = get_java_code([
        "v.tearDown();",
        "v.globalTearDown(true);"
    ]),
    measure_java:run_java_commands(false, 1, null,
        lists:flatten(Commands), null).

%%
%% Measure functions
%%
measure_channels() ->
    java:set_timeout(infinity),
    ClassPaths = ["../examples/vodkatv/src/", "../examples/vodkatv/vodkatv/"] ++
            filelib:wildcard("../examples/vodkatv/lib/*.jar"),
    Family = #family{initial = 0, grow = fun grow_channels/1},
    Axes = #axes{size = fun measure_size_channels/1,
                time = fun eval_cmds_channels/1,
                repeat = 5},
    {Time, _} = timer:tc(measure_java, measure_java,
        [1,  ?MAX_CHANNELS, Family, Axes, ClassPaths,
        fun global_setup_channels/0, fun global_teardown_channels/0]),
    Time / 1000000.

measure_epg() ->
    java:set_timeout(infinity),
    ClassPaths = ["../examples/vodkatv/src/", "../examples/vodkatv/vodkatv/"] ++
            filelib:wildcard("../examples/vodkatv/lib/*.jar"),
    Family = #family{initial = {0,0}, grow = fun grow_epg/1},
    Axes = #axes{size = fun measure_size_epg/1,
                time = fun eval_cmds_epg/1,
                repeat = 5},
    {Time, _} = timer:tc(measure_java, measure_java,
        [1,  ?MAX_CHANNELS, Family, Axes, ClassPaths,
        fun global_setup_epg/0, fun global_teardown_epg/0]),
    Time / 1000000.

measure_epg_memcached() ->
    java:set_timeout(infinity),
    ClassPaths = ["../examples/vodkatv/src/", "../examples/vodkatv/vodkatv/"] ++
            filelib:wildcard("../examples/vodkatv/lib/*.jar"),
    Family = #family{initial = 0, grow = fun grow_memcached/1},
    Axes = #axes{size = fun measure_size_memcached/1,
                time = fun eval_cmds_memcached/1,
                repeat = 5},
    {Time, _} = timer:tc(measure_java, measure_java,
        [1,  ?MAX_CHANNELS, Family, Axes, ClassPaths,
        fun global_setup_memcached/0, fun global_teardown_memcached/0]),
    Time / 1000000.

measure_epg_memcached_warmup() ->
    java:set_timeout(infinity),
    ClassPaths = ["../examples/vodkatv/src/", "../examples/vodkatv/vodkatv/"] ++
            filelib:wildcard("../examples/vodkatv/lib/*.jar"),
    Family = #family{initial = 0, grow = fun grow_memcached/1},
    Axes = #axes{size = fun measure_size_memcached/1,
                time = fun eval_cmds_memcached_warmup/1,
                repeat = 5},
    {Time, _} = timer:tc(measure_java, measure_java,
        [1,  ?MAX_CHANNELS, Family, Axes, ClassPaths,
        fun global_setup_memcached/0, fun global_teardown_memcached/0]),
    Time / 1000000.
